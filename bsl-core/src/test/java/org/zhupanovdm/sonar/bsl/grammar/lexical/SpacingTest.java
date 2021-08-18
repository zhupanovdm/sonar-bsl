package org.zhupanovdm.sonar.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class SpacingTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void empty() {
        assertThat(g.rule(BslGrammar.SPACING))
                .matches("");
    }

    @Test
    public void whitespace() {
        assertThat(g.rule(BslGrammar.SPACING))
                .matches(" ")
                .matches("\t")
                .matches("\n")
                .matches("\r")
                .matches("\r\n");
    }

    @Test
    public void comment() {
        assertThat(g.rule(BslGrammar.SPACING))
                .matches(" // comment")
                .matches(" // comment \n");
    }

}
