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
                .containsOnly(2, 4, 6, 7, 10, 11, 12, 16, 17, 20, 21, 24, 25, 27, 30, 31, 34, 36);
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
