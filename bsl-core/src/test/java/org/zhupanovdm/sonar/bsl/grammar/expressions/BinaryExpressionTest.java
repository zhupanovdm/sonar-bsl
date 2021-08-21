package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class BinaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void logic() {
        assertThat(g.rule(BslGrammar.LOGIC_OR_EXPRESSION)).matches("foo OR bar");
        assertThat(g.rule(BslGrammar.LOGIC_AND_EXPRESSION)).matches("foo AND bar");
        assertThat(g.rule(BslGrammar.LOGIC_NOT_EXPRESSION))
                .matches("NOT foo")
                .notMatches("NOT NOT foo")
                .matches("NOT (NOT foo)");
    }

    @Test
    public void relational() {
        assertThat(g.rule(BslGrammar.RELATIONAL_EXPRESSION))
                .matches("foo = bar")
                .matches("foo <> bar")
                .matches("foo > bar")
                .matches("foo >= bar")
                .matches("foo < bar")
                .matches("foo <= bar");
    }

    @Test
    public void additive() {
        assertThat(g.rule(BslGrammar.ADDITIVE_EXPRESSION))
                .matches("foo + bar")
                .matches("foo - bar");
    }

    @Test
    public void multiplicative() {
        assertThat(g.rule(BslGrammar.MULTIPLICATIVE_EXPRESSION))
                .matches("foo * bar")
                .matches("foo / bar")
                .matches("foo % bar");
    }

}
