package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.impl.ast.AstWalker;
import org.junit.Test;
import org.zhupanovdm.bsl.TestUtils;

import static org.fest.assertions.Assertions.assertThat;

public class FileLinesVisitorTest {

    @Test
    public void comments() {
        FileLinesVisitor linesVisitor = new FileLinesVisitor();
        AstWalker astWalker = new AstWalker(linesVisitor);
        astWalker.walkAndVisit(TestUtils.parse("/samples/metrics/Comments.bsl"));

        assertThat(linesVisitor.getLinesOfComments()).containsOnly(2, 7, 13);
        assertThat(linesVisitor.getLinesNoSonar()).containsOnly(9);
    }

    @Test
    public void lines_of_code() {
        FileLinesVisitor linesVisitor = new FileLinesVisitor();
        AstWalker astWalker = new AstWalker(linesVisitor);
        astWalker.walkAndVisit(TestUtils.parse("/samples/metrics/LinesOfCode.bsl"));

        assertThat(linesVisitor.getLinesOfCode()).containsOnly(5, 8, 10, 11, 13, 15, 17, 19);
    }

}
