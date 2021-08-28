package org.zhupanovdm.sonar.plugins.bsl;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.impl.Parser;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonarsource.analyzer.commons.ProgressReport;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.lexer.BslLexer;
import org.zhupanovdm.bsl.metrics.ComplexityVisitor;
import org.zhupanovdm.bsl.metrics.FileLinesVisitor;
import org.zhupanovdm.bsl.metrics.ModuleMetrics;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.sonar.api.batch.fs.InputFile.Type.MAIN;

public class BslSensor implements Sensor {

    private static final Logger LOG = Loggers.get(BslSensor.class);

    private final FileLinesContextFactory fileLinesContextFactory;

    public BslSensor(FileLinesContextFactory fileLinesContextFactory) {
        this.fileLinesContextFactory = fileLinesContextFactory;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .name("BSL")
                .onlyOnFileType(MAIN)
                .onlyOnLanguage(Bsl.KEY);
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicates predicates = fs.predicates();
        FilePredicate predicate = predicates.and(
                predicates.hasType(MAIN),
                predicates.hasLanguage(Bsl.KEY),
                file -> !file.uri().getPath().endsWith("mxml"));

        List<InputFile> files = new LinkedList<>();
        fs.inputFiles(predicate).forEach(files::add);

        ProgressReport report = new ProgressReport("Report about progress of the SonarSource BSL analyzer", SECONDS.toMillis(10), LOG, "analyzed");
        List<String> filenames = files.stream().map(InputFile::toString).collect(Collectors.toList());
        report.start(filenames);
        for (InputFile file : files) {
            analyseFile(context, file);
            report.nextFile();
        }
        report.stop();
    }

    private void analyseFile(SensorContext context, InputFile file) {
        String contents;
        try {
            contents = file.contents();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + file, e);
        }

        Charset charset = file.charset();
        Parser<LexerlessGrammar> parser = BslParser.create(charset);
        AstNode tree = null;
        try {
            tree = parser.parse(contents);
        } catch (RecognitionException e) {
            LOG.error("Unable to parse file: {}", file);
            LOG.error(e.getMessage());
        }

        saveMeasures(context, file, tree);
        new BslTokensVisitor(context, BslLexer.create(charset), file, contents).scanFile(tree);
    }

    private void saveMeasures(SensorContext context, InputFile file, AstNode tree) {
        FileLinesVisitor fileLinesMetrics = new FileLinesVisitor();
        fileLinesMetrics.scanFile(tree);

        ComplexityVisitor complexityMetrics = new ComplexityVisitor();
        complexityMetrics.scanFile(tree);

        ModuleMetrics moduleMetrics = new ModuleMetrics(tree);

        saveMeasure(context, file, CoreMetrics.NCLOC, fileLinesMetrics.getLinesOfCode().size());
        saveMeasure(context, file, CoreMetrics.COMMENT_LINES, fileLinesMetrics.getLinesOfComments().size());
        saveMeasure(context, file, CoreMetrics.FUNCTIONS, moduleMetrics.getNumberOfFunctions());
        saveMeasure(context, file, CoreMetrics.STATEMENTS, moduleMetrics.getNumberOfStatements());
        saveMeasure(context, file, CoreMetrics.EXECUTABLE_LINES_DATA, moduleMetrics.getExecutableLines());

        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(file);
        fileLinesMetrics.getLinesOfCode().forEach(line -> fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1));
        fileLinesContext.save();

        saveMeasure(context, file, CoreMetrics.COMPLEXITY, complexityMetrics.getComplexity());
    }

    private static <T extends Serializable> void saveMeasure(SensorContext context, InputFile file, Metric<T> metric, T value) {
        context.<T>newMeasure().on(file).forMetric(metric).withValue(value).save();
    }

}
