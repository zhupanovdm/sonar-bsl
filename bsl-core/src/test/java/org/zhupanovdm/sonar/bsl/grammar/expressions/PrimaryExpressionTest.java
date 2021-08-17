package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class PrimaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void primaryExpression() {
        Assertions.assertThat(g.rule(BslGrammar.PRIMARY_EXPRESSION))
                .matches("1")
                .matches("foo")
                .matches("NULL")
                .matches("(a)")
                .matches("((a))")
                .matches("(1 + 1)")
                .notMatches("()")
                .notMatches("1 + 1");
    }

}
