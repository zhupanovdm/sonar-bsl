package org.zhupanovdm.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.SPACING;

public class SpacingTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void empty() {
        assertThat(g.rule(SPACING))
                .matches("");
    }

    @Test
    public void whitespace() {
        assertThat(g.rule(SPACING))
                .matches(" ")
                .matches("\t")
                .matches("\n")
                .matches("\r")
                .matches("\r\n");
    }

    @Test
    public void comment() {
        assertThat(g.rule(SPACING))
                .matches(" // comment")
                .matches(" // comment \n");
    }

}
