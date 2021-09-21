package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.*;

public class PostfixExpressionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void indexOperator() {
        assertThat(g.rule(INDEX_OPERATOR))
                .matches("[1]")
                .matches("[x + y]")
                .notMatches("[]");
    }

    @Test
    public void callOperator() {
        assertThat(g.rule(CALL_OPERATOR))
                .matches("()")
                .matches("(a)")
                .matches("(a, b)")
                .matches("(,a,, b, c,)")
                .notMatches("(a b)");
    }

    @Test
    public void postfix() {
        assertThat(g.rule(POSTFIX_EXPR))
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
                .matches("foo.bar()")
                .matches("запрос.выполнить()")

                .matches("foo().bar[0]")
                .matches("foo().bar")
                .matches("foo().bar()")

                .notMatches("foo()()")
                .notMatches("foo[0]()");
    }

    @Test
    public void assignable() {
        assertThat(g.rule(ASSIGNABLE_EXPR))
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
        assertThat(g.rule(CALLABLE_EXPR))
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
