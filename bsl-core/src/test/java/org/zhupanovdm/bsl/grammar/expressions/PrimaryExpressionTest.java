package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.PRIMARY_EXPRESSION;

public class PrimaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void primaryExpression() {
        assertThat(g.rule(PRIMARY_EXPRESSION))
                .matches("1")
                .matches("foo")
                .matches("null")
                .matches("(a)")
                .matches("((a))")
                .matches("(1 + 1)")
                .notMatches("()")
                .notMatches("1 + 1");
    }

}
