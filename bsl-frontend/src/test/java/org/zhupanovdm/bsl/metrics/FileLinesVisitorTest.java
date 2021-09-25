package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class FileLinesVisitorTest {

    @Test
    public void comments() {
        FileLinesVisitor subscriber = new FileLinesVisitor();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/Comments.bsl"));

        assertThat(subscriber.getLinesOfComments()).containsOnly(2, 7, 13);
        assertThat(subscriber.getLinesNoSonar()).containsOnly(9);
    }

    @Test
    public void linesOfCode() {
        FileLinesVisitor subscriber = new FileLinesVisitor();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/LinesOfCode.bsl"));

        assertThat(subscriber.getLinesOfCode()).containsOnly(5, 8, 10, 11, 13, 15, 17, 19);
    }

}
