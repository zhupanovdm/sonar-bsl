package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.module;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class ModuleMetricsTest {
    @Test
    public void statements() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(moduleFile("/samples/metrics/Statements.bsl"), subscriber);

        assertThat(subscriber.getNumberOfStatements()).isEqualTo(20);
    }

    @Test
    public void emptyStatements() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(module(";; a = 5;;; b();;"), subscriber);

        assertThat(subscriber.getNumberOfStatements()).isEqualTo(8);
    }

    @Test
    public void functions() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(moduleFile("/samples/metrics/Functions.bsl"), subscriber);

        assertThat(subscriber.getNumberOfFunctions()).isEqualTo(4);
    }

    @Test
    public void executableLines() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(moduleFile("/samples/metrics/Statements.bsl"), subscriber);

        assertThat(subscriber.getExecutableLines())
                .isEqualTo("2=1;4=1;6=1;7=1;10=1;11=1;12=1;16=1;17=1;20=1;21=1;24=1;25=1;27=1;30=1;31=1;34=1;36=1;");
    }

    @Test
    public void comments() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(moduleFile("/samples/metrics/Comments.bsl"), subscriber);

        assertThat(subscriber.getLinesOfComments()).containsOnly(2, 7, 13);
        assertThat(subscriber.getLinesNoSonar()).containsOnly(9);
    }

    @Test
    public void linesOfCode() {
        ModuleMetrics subscriber = new ModuleMetrics();

        BslTreePublisher.publish(moduleFile("/samples/metrics/LinesOfCode.bsl"), subscriber);

        assertThat(subscriber.getLinesOfCode()).containsOnly(5, 8, 10, 11, 13, 15, 17, 19);
    }
}
