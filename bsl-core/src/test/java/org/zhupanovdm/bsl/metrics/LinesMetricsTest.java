package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.TestBslParser;

import static org.fest.assertions.Assertions.assertThat;

public class LinesMetricsTest {

    @Test
    public void comments() {
        LinesMetrics metrics = new LinesMetrics(TestBslParser.parse("Comments.bsl"));
        assertThat(metrics.getLinesOfComments()).containsOnly(2, 7, 13);
        assertThat(metrics.getLinesNoSonar()).containsOnly(9);
    }

    @Test
    public void lines_of_code() {
        LinesMetrics metrics = new LinesMetrics(TestBslParser.parse("LinesOfCode.bsl"));
        assertThat(metrics.getLinesOfCode()).containsOnly(5, 8, 10, 11, 13, 15, 17, 19);
    }

}
