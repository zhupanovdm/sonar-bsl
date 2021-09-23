package org.zhupanovdm.bsl.metrics;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.moduleFile;

public class CognitiveComplexityVisitorStubTest {
    @Test
    public void test() {
        assertThat(CognitiveComplexityVisitorStub.complexity(moduleFile("/samples/metrics/CognitiveComplexity.bsl")))
                .isEqualTo(0);
    }
}