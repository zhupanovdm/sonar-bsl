package org.zhupanovdm.sonar.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.SPACING_NO_LB;

public class SpacingNoLineBreakTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void empty() {
        assertThat(g.rule(SPACING_NO_LB))
                .matches("");
    }

    @Test
    public void whitespace() {
        assertThat(g.rule(SPACING_NO_LB))
                .matches(" ")
                .matches("\t")
                .notMatches("\n")
                .notMatches("\r")
                .notMatches("\r\n");
    }

    @Test
    public void comment() {
        assertThat(g.rule(SPACING_NO_LB))
                .matchesPrefix(" // comment", "\n");
    }

}
