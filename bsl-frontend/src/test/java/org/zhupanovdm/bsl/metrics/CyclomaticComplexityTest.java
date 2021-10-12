package org.zhupanovdm.bsl.metrics;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.moduleFile;
import static org.zhupanovdm.bsl.tree.BslTreePublisher.scan;

public class CyclomaticComplexityTest {
    @Test
    public void test() {
        CyclomaticComplexity subscriber = new CyclomaticComplexity();

        scan(moduleFile("/samples/metrics/CyclomaticComplexity.bsl"), subscriber);

        assertThat(subscriber.getComplexity()).isEqualTo(3);
    }
}
