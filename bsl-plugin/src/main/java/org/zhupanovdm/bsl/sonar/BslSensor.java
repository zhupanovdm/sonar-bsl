package org.zhupanovdm.bsl.sonar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.ast.AstWalker;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonarsource.analyzer.commons.ProgressReport;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.checks.CheckList;
import org.zhupanovdm.bsl.metrics.CognitiveComplexityVisitorStub;
import org.zhupanovdm.bsl.metrics.CyclomaticComplexityVisitor;
import org.zhupanovdm.bsl.metrics.FileLinesVisitor;
import org.zhupanovdm.bsl.metrics.ModuleMetrics;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.BslTreePublisher;
import org.zhupanovdm.bsl.tree.module.Module;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BslSensor implements Sensor {

    private static final Logger LOG = Loggers.get(BslSensor.class);

    private final Checks<BslCheck> checks;
    private final FileLinesContextFactory fileLinesContextFactory;

    public BslSensor(CheckFactory checkFactory, FileLinesContextFactory fileLinesContextFactory) {
        this.fileLinesContextFactory = fileLinesContextFactory;
        this.checks = checkFactory
                .<BslCheck>create(CheckList.REPOSITORY_KEY)
                .addAnnotatedChecks(CheckList.getChecks());
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .name("BSL Sensor")
                .onlyOnLanguage(Bsl.KEY)
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicates predicates = fs.predicates();
        FilePredicate predicate = predicates.and(
                predicates.hasType(InputFile.Type.MAIN),
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
        AstNode tree;
        try {
            tree = parser.parse(contents);
        } catch (RecognitionException e) {
            LOG.error("Unable to parse file: {}", file);
            LOG.error(e.getMessage());
            throw new IllegalStateException("Cannot parse " + file, e);
        }

        BiConsumer<BslCheck, Issue> issueConsumer = (check, issue) -> saveIssue(context, check, issue, file);

        List<BslCheck> allChecks = new LinkedList<>(checks.all());
        allChecks.forEach(check -> check.setIssueConsumer(issueConsumer));
        new AstWalker(allChecks).walkAndVisit(tree);

        saveMeasures(context, file, tree);
        new BslLexScanner(context).scan(file, contents);
    }

    private void saveIssue(SensorContext context, BslCheck check, Issue checkIssue, InputFile file) {
        RuleKey ruleKey = Objects.requireNonNull(checks.ruleKey(check), "Rule key for check is required");
        NewIssue issue = context.newIssue();
        NewIssueLocation location = issue.newLocation().on(file).message(checkIssue.message());
        Integer line = checkIssue.line();
        if (line != null) {
            location.at(file.selectLine(line));
        }
        Double cost = checkIssue.cost();
        if (cost != null) {
            issue.gap(cost);
        }
        issue.at(location).forRule(ruleKey).save();
    }

    private void saveMeasures(SensorContext context, InputFile file, AstNode tree) {
        Module module = new BslTreeCreator().create(tree);

        CognitiveComplexityVisitorStub cognitiveComplexity = new CognitiveComplexityVisitorStub();
        CyclomaticComplexityVisitor cyclomaticComplexity = new CyclomaticComplexityVisitor();
        FileLinesVisitor fileLines = new FileLinesVisitor();
        ModuleMetrics moduleMetrics = new ModuleMetrics();

        new BslTreePublisher()
                .subscribe(cyclomaticComplexity)
                .subscribe(cognitiveComplexity)
                .subscribe(fileLines)
                .subscribe(moduleMetrics)
                .publish(module);

        saveMeasure(context, file, CoreMetrics.NCLOC, fileLines.getLinesOfCode().size());
        saveMeasure(context, file, CoreMetrics.COMMENT_LINES, fileLines.getLinesOfComments().size());
        saveMeasure(context, file, CoreMetrics.FUNCTIONS, moduleMetrics.getNumberOfFunctions());
        saveMeasure(context, file, CoreMetrics.STATEMENTS, moduleMetrics.getNumberOfStatements());
        saveMeasure(context, file, CoreMetrics.EXECUTABLE_LINES_DATA, moduleMetrics.getExecutableLines());
        saveMeasure(context, file, CoreMetrics.COMPLEXITY, cyclomaticComplexity.getComplexity());
        saveMeasure(context, file, CoreMetrics.COGNITIVE_COMPLEXITY, cognitiveComplexity.getComplexity());

        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(file);
        fileLines.getLinesOfCode().forEach(line -> fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1));
        fileLinesContext.save();
    }

    private static <T extends Serializable> void saveMeasure(SensorContext context, InputFile file, Metric<T> metric, T value) {
        context.<T>newMeasure().on(file).forMetric(metric).withValue(value).save();
    }

}
