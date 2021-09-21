package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.impl.ast.AstWalker;
import org.junit.Test;
import org.zhupanovdm.bsl.TestUtils;

import static org.fest.assertions.Assertions.assertThat;

public class CyclomaticComplexityVisitorTest {

    @Test
    public void test() {
        CyclomaticComplexityVisitor cyclomaticComplexityVisitor = new CyclomaticComplexityVisitor();
        AstWalker astWalker = new AstWalker(cyclomaticComplexityVisitor);
        astWalker.walkAndVisit(TestUtils.parseFile("/samples/metrics/CyclomaticComplexity.bsl"));

        assertThat(cyclomaticComplexityVisitor.getComplexity()).isEqualTo(3);
    }

}
