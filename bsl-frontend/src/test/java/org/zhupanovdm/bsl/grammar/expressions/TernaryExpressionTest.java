package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslGrammar.TERNARY_EXPR;

public class TernaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(TERNARY_EXPR))
                .matches("?(1 + 1, 1 + 1, 1 + 1)")
                .notMatches("?()")
                .notMatches("?(1 + 1, 1 + 1)")
                .notMatches("?(1 + 1, 1 + 1, 1 + 1, 1 + 1)")
                .notMatches("?(,,)");
    }

}
