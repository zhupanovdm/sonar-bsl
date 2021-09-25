package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.tree.BslTreePublisher;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class CognitiveComplexityVisitorStubTest {
    @Test
    public void test() {
        CognitiveComplexityVisitorStub subscriber = new CognitiveComplexityVisitorStub();

        new BslTreePublisher().subscribe(subscriber).publish(moduleFile("/samples/metrics/CognitiveComplexity.bsl"));

        assertThat(subscriber.getComplexity()).isEqualTo(0);
    }
}