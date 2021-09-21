package org.zhupanovdm.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.SPACING;

public class SpacingTest {

    private final LexerlessGrammar g = BslGrammar.create();

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
