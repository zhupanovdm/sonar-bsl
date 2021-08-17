package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class PostfixExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void postfixExpression() {
        Assertions.assertThat(g.rule(BslGrammar.POSTFIX_EXPRESSION))
                .matches("foo[0]")
                .matches("foo[1][2][3]")
                .matches("a.b.c.d.e.f")
                .matches("foo()")
                .matches("foo()[0]")

                .matches("foo[0].bar[0]")
                .matches("foo[0].bar")
                .matches("foo[0].bar()")

                .matches("foo.bar[0]")
                .matches("foo.bar")
                .matches("foo.bar()")

                .matches("foo().bar[0]")
                .matches("foo().bar")
                .matches("foo().bar()")

                .notMatches("foo()()")
                .notMatches("foo[0]()");
    }

    @Test
    public void indexOperator() {
        Assertions.assertThat(g.rule(BslGrammar.INDEX_OPERATOR))
                .matches("[1]")
                .matches("[x + y]")
                .notMatches("[]");
    }

    @Test
    public void callOperator() {
        Assertions.assertThat(g.rule(BslGrammar.CALL_OPERATOR))
                .matches("()")
                .matches("(a)")
                .matches("(a, b)")
                .matches("(,a,, b, c,)");
    }

}
