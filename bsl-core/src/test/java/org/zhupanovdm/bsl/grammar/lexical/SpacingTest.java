package org.zhupanovdm.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.SPACING;

public class SpacingTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(SPACING))
                .matches("")
                .matches(" ")
                .matches("\t")
                .matches("\n")
                .matches("\r")
                .matches("\r\n")
                .matches(" // comment")
                .matches(" // comment \n");
    }

}
