package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.impl.ast.AstWalker;
import org.junit.Test;
import org.zhupanovdm.bsl.TestUtils;

import static org.fest.assertions.Assertions.assertThat;

public class ComplexityVisitorTest {

    @Test
    public void test() {
        ComplexityVisitor complexityVisitor = new ComplexityVisitor();
        AstWalker astWalker = new AstWalker(complexityVisitor);
        astWalker.walkAndVisit(TestUtils.parse("/samples/metrics/Complexity.bsl"));

        assertThat(complexityVisitor.getComplexity()).isEqualTo(3);
    }

}
