package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.moduleFile;

public class CyclomaticComplexityTest {
    @Test
    public void test() {
        CyclomaticComplexity subscriber = new CyclomaticComplexity();

        BslTreePublisher.publish(moduleFile("/samples/metrics/CyclomaticComplexity.bsl"), subscriber);

        assertThat(subscriber.getComplexity()).isEqualTo(3);
    }
}
