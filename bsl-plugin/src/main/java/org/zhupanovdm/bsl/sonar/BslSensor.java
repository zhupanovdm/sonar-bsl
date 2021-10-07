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
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.zhupanovdm.bsl.Check;
import org.zhupanovdm.bsl.checks.CheckList;

import java.util.*;

import static org.sonar.api.batch.fs.InputFile.Type.MAIN;

public class BslSensor implements Sensor {
    private final FileLinesContextFactory fileLinesContextFactory;
    private final NoSonarFilter noSonarFilter;
    private final BslChecks checks;

    public BslSensor(FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory, NoSonarFilter noSonarFilter) {
        this.fileLinesContextFactory = fileLinesContextFactory;
        this.checks = new BslChecks(checkFactory);
        this.noSonarFilter = noSonarFilter;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .name("BSL Sensor")
                .onlyOnLanguage(Bsl.KEY)
                .onlyOnFileType(MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicates predicates = fs.predicates();
        FilePredicate predicate = predicates.and(predicates.hasType(MAIN), predicates.hasLanguage(Bsl.KEY));

        List<InputFile> files = new LinkedList<>();
        fs.inputFiles(predicate).forEach(files::add);

        BslScanner scanner = new BslScanner(context, checks, fileLinesContextFactory, noSonarFilter);
        scanner.execute(files);
    }
}
