package org.zhupanovdm.bsl.sonar;

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
import org.sonarsource.analyzer.commons.ProgressReport;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.checks.CheckList;
import org.zhupanovdm.bsl.metrics.CognitiveComplexity;
import org.zhupanovdm.bsl.metrics.CyclomaticComplexity;
import org.zhupanovdm.bsl.metrics.ModuleMetrics;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import java.io.Serializable;
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
            analyseFile(context, new BslModuleContext(file));
            report.nextFile();
        }
        report.stop();
    }

    private void analyseFile(SensorContext context, BslModuleContext module) {
        BslTreePublisher publisher = new BslTreePublisher();

        BiConsumer<BslCheck, Issue> issueConsumer = (check, issue) -> saveIssue(context, check, issue, module);
        checks.all().forEach(check -> {
            publisher.subscribe(check);
            check.setIssueConsumer(issueConsumer);
        });

        ModuleMetrics metrics = new ModuleMetrics();
        CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
        CognitiveComplexity cognitiveComplexity = new CognitiveComplexity();

        publisher.subscribe(metrics, cyclomaticComplexity, cognitiveComplexity);
        publisher.init();
        publisher.publish(module.getEntry());

        saveMeasures(context, module, metrics, cyclomaticComplexity, cognitiveComplexity);
        new BslLexScanner(context).scan(module);
    }

    private void saveIssue(SensorContext context, BslCheck check, Issue checkIssue, BslModuleContext module) {
        RuleKey ruleKey = Objects.requireNonNull(checks.ruleKey(check), "Rule key for check is required");
        InputFile file = module.getFile();
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

    private void saveMeasures(SensorContext context,
                              BslModuleContext module,
                              ModuleMetrics metrics,
                              CyclomaticComplexity cyclomaticComplexity,
                              CognitiveComplexity cognitiveComplexity) {

        saveMeasure(context, module, CoreMetrics.NCLOC, metrics.getLinesOfCode().size());
        saveMeasure(context, module, CoreMetrics.COMMENT_LINES, metrics.getLinesOfComments().size());
        saveMeasure(context, module, CoreMetrics.FUNCTIONS, metrics.getNumberOfFunctions());
        saveMeasure(context, module, CoreMetrics.STATEMENTS, metrics.getNumberOfStatements());
        saveMeasure(context, module, CoreMetrics.EXECUTABLE_LINES_DATA, metrics.getExecutableLines());
        saveMeasure(context, module, CoreMetrics.COMPLEXITY, cyclomaticComplexity.getComplexity());
        saveMeasure(context, module, CoreMetrics.COGNITIVE_COMPLEXITY, cognitiveComplexity.getComplexity());

        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(module.getFile());
        metrics.getLinesOfCode().forEach(line -> fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1));
        fileLinesContext.save();
    }

    private static <T extends Serializable> void saveMeasure(SensorContext context, BslModuleContext module, Metric<T> metric, T value) {
        context.<T>newMeasure().on(module.getFile()).forMetric(metric).withValue(value).save();
    }
}
