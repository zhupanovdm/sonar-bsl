package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class CyclomaticComplexityVisitorTest {
    @Test
    public void test() {
        CyclomaticComplexityVisitor subscriber = new CyclomaticComplexityVisitor();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/CyclomaticComplexity.bsl"));

        assertThat(subscriber.getComplexity()).isEqualTo(3);
    }
}
