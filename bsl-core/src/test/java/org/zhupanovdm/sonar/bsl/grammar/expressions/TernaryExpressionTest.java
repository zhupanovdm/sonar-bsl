package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class TernaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.TERNARY_EXPRESSION))
                .matches("?(true, true, false)")
                .notMatches("?()")
                .notMatches("?(true, true)")
                .notMatches("?(true, true, false, null)")
                .notMatches("?(,,)");
    }

}
