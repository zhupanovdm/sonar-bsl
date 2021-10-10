package org.zhupanovdm.bsl.sonar;


import com.sonar.sslr.api.RecognitionException;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.ProgressReport;
import org.zhupanovdm.bsl.metrics.CognitiveComplexity;
import org.zhupanovdm.bsl.metrics.CyclomaticComplexity;
import org.zhupanovdm.bsl.metrics.ModuleMetrics;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BslScanner {
    private static final Logger LOG = Loggers.get(BslScanner.class);
    private static final String FAIL_FAST_PROPERTY_NAME = "sonar.internal.analysis.failFast";

    private final SensorContext context;
    private final BslChecks checks;
    private final FileLinesContextFactory fileLinesContextFactory;
    private final NoSonarFilter noSonarFilter;

    public BslScanner(SensorContext context, BslChecks checks, FileLinesContextFactory fileLinesContextFactory, NoSonarFilter noSonarFilter) {
        this.context = context;
        this.checks = checks;
        this.fileLinesContextFactory = fileLinesContextFactory;
        this.noSonarFilter = noSonarFilter;
    }

    public void execute(Collection<InputFile> files) {
        ProgressReport report = new ProgressReport("Report about progress of the SonarSource BSL analyzer", SECONDS.toMillis(10), LOG, "analyzed");
        LOG.info("Starting SonarSource BSL analyzer");

        List<String> filenames = files.stream().map(InputFile::toString).collect(Collectors.toList());

        report.start(filenames);
        for (InputFile file : files) {
            if (context.isCancelled()) {
                report.cancel();
                return;
            }

            try {
                scanFile(file);
            } catch (RecognitionException ex) {
                context.newAnalysisError().onFile(file)
                        .at(file.newPointer(ex.getLine(), 0))
                        .message(ex.getMessage())
                        .save();
                processException(ex, file);
            } catch (Exception ex) {
                context.newAnalysisError().onFile(file)
                        .message(ex.getMessage())
                        .save();
                processException(ex, file);
            } finally {
                report.nextFile();
            }
        }

        report.stop();
    }

    private void processException(Exception ex, InputFile file) {
        LOG.error("Unable to parse file: " + file);
        LOG.error(ex.getMessage());

        if (context.config().getBoolean(FAIL_FAST_PROPERTY_NAME).orElse(false)) {
            throw new IllegalStateException("Exception when analyzing " + file, ex);
        }
    }

    private void scanFile(InputFile file) {
        BslModuleContext module = new BslModuleContext(new BslModuleFile(file), context, checks);

        ModuleMetrics metrics = new ModuleMetrics();
        CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
        CognitiveComplexity cognitiveComplexity = new CognitiveComplexity();

        BslTreePublisher publisher = new BslTreePublisher();
        publisher.subscribe(module.getChecks().all());
        publisher.subscribe(
                metrics,
                cyclomaticComplexity,
                cognitiveComplexity,
                new BslModuleCpdAnalyzer(module),
                new BslModuleHighlighter(module));
        publisher.init();
        publisher.scan(module);

        saveMeasures(module, metrics, cyclomaticComplexity, cognitiveComplexity);
    }

    private void saveMeasures(BslModuleContext module,
                              ModuleMetrics metrics,
                              CyclomaticComplexity cyclomaticComplexity,
                              CognitiveComplexity cognitiveComplexity) {

        InputFile file = module.getFile().getInput();

        noSonarFilter.noSonarInFile(file, metrics.getLinesNoSonar());

        module.saveMeasure(CoreMetrics.NCLOC, metrics.getLinesOfCode().size());
        module.saveMeasure(CoreMetrics.COMMENT_LINES, metrics.getLinesOfComments().size());
        module.saveMeasure(CoreMetrics.FUNCTIONS, metrics.getNumberOfFunctions());
        module.saveMeasure(CoreMetrics.STATEMENTS, metrics.getNumberOfStatements());
        module.saveMeasure(CoreMetrics.COMPLEXITY, cyclomaticComplexity.getComplexity());
        module.saveMeasure(CoreMetrics.COGNITIVE_COMPLEXITY, cognitiveComplexity.getComplexity());

        FileLinesContext fileLines = fileLinesContextFactory.createFor(file);
        metrics.getLinesOfCode().forEach(line -> fileLines.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1));
        metrics.getExecutableLines().forEach(line -> fileLines.setIntValue(CoreMetrics.EXECUTABLE_LINES_DATA_KEY, line, 1));
        fileLines.save();
    }
}
