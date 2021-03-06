package org.zhupanovdm.bsl.grammar.lexical;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.SPACING_NO_LB;

public class SpacingNoLineBreakTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(SPACING_NO_LB))
                .matches("")
                .matches(" ")
                .matches("\t")
                .matchesPrefix(" // comment", "\n")
                .notMatches("\n")
                .notMatches("\r")
                .notMatches("\r\n");
    }

}
