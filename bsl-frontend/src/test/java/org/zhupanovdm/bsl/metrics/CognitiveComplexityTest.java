package org.zhupanovdm.bsl.metrics;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.moduleFile;
import static org.zhupanovdm.bsl.tree.BslTreePublisher.scan;

public class CognitiveComplexityTest {
    @Test
    public void test() {
        CognitiveComplexity subscriber = new CognitiveComplexity();

        scan(moduleFile("/samples/metrics/CognitiveComplexity.bsl"), subscriber);

        assertThat(subscriber.getComplexity()).isEqualTo(0);
    }
}