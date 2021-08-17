package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class TernaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.TERNARY_EXPRESSION))
                .matches("?(true, true, false)")
                .notMatches("?()")
                .notMatches("?(true, true)")
                .notMatches("?(true, true, false, null)")
                .notMatches("?(,,)");
    }

}
