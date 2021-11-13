package org.zhupanovdm.bsl.sonar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.LogTester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BslSensorTest {

    private static Path workDir;

    private final File baseDir = new File("src/test/resources/org/zhupanovdm/bsl/sonar/sensor").getAbsoluteFile();

    private SensorContextTester context;

    private ActiveRules activeRules;

    @Rule
    public LogTester logTester = new LogTester();

    @Before
    public void init() throws IOException {
        context = SensorContextTester.create(baseDir);
        workDir = Files.createTempDirectory("workDir");
        context.fileSystem().setWorkDir(workDir);
    }

    @Test
    public void sensor_descriptor() {
        activeRules = new ActiveRulesBuilder().build();
        DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
        sensor().describe(descriptor);

        assertThat(descriptor.name()).isEqualTo("BSL Sensor");
        assertThat(descriptor.languages()).containsOnly("bsl");
        assertThat(descriptor.type()).isEqualTo(InputFile.Type.MAIN);
    }

    private BslSensor sensor() {
        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        CheckFactory checkFactory = new CheckFactory(activeRules);
        NoSonarFilter noSonarFilter = mock(NoSonarFilter.class);
        return new BslSensor(fileLinesContextFactory, checkFactory, noSonarFilter);
    }
}