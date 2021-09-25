package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class CyclomaticComplexityTest {
    @Test
    public void test() {
        CyclomaticComplexity subscriber = new CyclomaticComplexity();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/CyclomaticComplexity.bsl"));

        assertThat(subscriber.getComplexity()).isEqualTo(3);
    }
}
