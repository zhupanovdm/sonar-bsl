package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class PostfixExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

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
    public void assignable() {
        Assertions.assertThat(g.rule(BslGrammar.ASSIGNABLE))
                .matches("a")
                .matches("a[0]")
                .matches("a.b.c")
                .matches("a.b.c[0]")
                .matches("a()[0]")
                .matches("a().b")
                .notMatches("1")
                .notMatches("1 + 1")
                .notMatches("a()")
                .notMatches("a.b()");
    }

    @Test
    public void callable() {
        Assertions.assertThat(g.rule(BslGrammar.CALLABLE))
                .matches("a()")
                .matches("a.b()")
                .matches("a[0].b()")
                .matches("a().b()")
                .notMatches("1")
                .notMatches("1 + 1")
                .notMatches("a")
                .notMatches("a[0]")
                .notMatches("a.b")
                .notMatches("a.b[0]");
    }

}
