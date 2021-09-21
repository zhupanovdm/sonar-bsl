package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslGrammar.*;

public class BinaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void logic() {
        assertThat(g.rule(OR_EXPR)).matches("foo Or bar");
        assertThat(g.rule(AND_EXPR)).matches("foo And bar");
        assertThat(g.rule(NOT_EXPR))
                .matches("Not foo")
                .matches("Not (Not foo)")
                .notMatches("Not Not foo");
    }

    @Test
    public void relational() {
        assertThat(g.rule(RELATIONAL_EXPR))
                .matches("foo = bar")
                .matches("foo <> bar")
                .matches("foo > bar")
                .matches("foo >= bar")
                .matches("foo < bar")
                .matches("foo <= bar");
    }

    @Test
    public void additive() {
        assertThat(g.rule(ADDITIVE_EXPR))
                .matches("foo + bar")
                .matches("foo - bar");
    }

    @Test
    public void multiplicative() {
        assertThat(g.rule(MULTIPLICATIVE_EXPR))
                .matches("foo * bar")
                .matches("foo / bar")
                .matches("foo % bar");
    }

}
