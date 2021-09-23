package org.zhupanovdm.bsl.metrics;

import org.junit.Test;
import org.zhupanovdm.bsl.TestUtils;

import static org.fest.assertions.Assertions.assertThat;

public class CyclomaticComplexityVisitorTest {
    @Test
    public void test() {
        assertThat(CyclomaticComplexityVisitor.complexity(TestUtils.moduleFile("/samples/metrics/CyclomaticComplexity.bsl")))
                .isEqualTo(3);
    }
}
