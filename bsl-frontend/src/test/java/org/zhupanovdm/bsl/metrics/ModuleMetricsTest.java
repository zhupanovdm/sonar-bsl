package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;
import org.zhupanovdm.bsl.tree.module.Module;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.module;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class ModuleMetricsTest {
    @Test
    public void statements() {
        ModuleMetrics subscriber = new ModuleMetrics();

        Module module = moduleFile("/samples/metrics/Statements.bsl");
        new BslTreePublisher().subscribe(subscriber).publish(module);

        assertThat(subscriber.getNumberOfStatements()).isEqualTo(18);
    }

    @Test
    public void emptyStatements() {
        ModuleMetrics subscriber = new ModuleMetrics();

        new BslTreePublisher().subscribe(subscriber).publish(module(";; a = 5;;; b();;"));

        assertThat(subscriber.getNumberOfStatements()).isEqualTo(8);
    }

    @Test
    public void functions() {
        ModuleMetrics subscriber = new ModuleMetrics();

        Module module = moduleFile("/samples/metrics/Functions.bsl");
        new BslTreePublisher().subscribe(subscriber).publish(module);

        assertThat(subscriber.getNumberOfFunctions()).isEqualTo(4);
    }

    @Test
    public void executableLines() {
        ModuleMetrics subscriber = new ModuleMetrics();

        Module module = moduleFile("/samples/metrics/Statements.bsl");
        new BslTreePublisher().subscribe(subscriber).publish(module);

        assertThat(subscriber.getExecutableLines())
                .isEqualTo("2=1;4=1;6=1;7=1;8=1;12=1;13=1;16=1;17=1;20=1;21=1;23=1;26=1;27=1;29=1;31=1;33=1;");
    }

    @Test
    public void comments() {
        ModuleMetrics subscriber = new ModuleMetrics();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/Comments.bsl"));

        assertThat(subscriber.getLinesOfComments()).containsOnly(2, 7, 13);
        assertThat(subscriber.getLinesNoSonar()).containsOnly(9);
    }

    @Test
    public void linesOfCode() {
        ModuleMetrics subscriber = new ModuleMetrics();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/LinesOfCode.bsl"));

        assertThat(subscriber.getLinesOfCode()).containsOnly(5, 8, 10, 11, 13, 15, 17, 19);
    }
}
