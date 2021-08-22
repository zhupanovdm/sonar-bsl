package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.TERNARY_EXPRESSION;

public class TernaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(TERNARY_EXPRESSION))
                .matches("?(true, true, false)")
                .notMatches("?()")
                .notMatches("?(true, true)")
                .notMatches("?(true, true, false, null)")
                .notMatches("?(,,)");
    }

}
