package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.*;

public class BinaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void logic() {
        assertThat(g.rule(OR_EXPRESSION)).matches("foo or bar");
        assertThat(g.rule(AND_EXPRESSION)).matches("foo and bar");
        assertThat(g.rule(NOT_EXPRESSION))
                .matches("not foo")
                .matches("not (not foo)")
                .notMatches("not not foo");
    }

    @Test
    public void relational() {
        assertThat(g.rule(RELATIONAL_EXPRESSION))
                .matches("foo = bar")
                .matches("foo <> bar")
                .matches("foo > bar")
                .matches("foo >= bar")
                .matches("foo < bar")
                .matches("foo <= bar");
    }

    @Test
    public void additive() {
        assertThat(g.rule(ADDITIVE_EXPRESSION))
                .matches("foo + bar")
                .matches("foo - bar");
    }

    @Test
    public void multiplicative() {
        assertThat(g.rule(MULTIPLICATIVE_EXPRESSION))
                .matches("foo * bar")
                .matches("foo / bar")
                .matches("foo % bar");
    }

}
