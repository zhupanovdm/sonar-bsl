package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class BinaryExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void logic() {
        Assertions.assertThat(g.rule(BslGrammar.LOGIC_OR_EXPRESSION))
                .matches("foo OR bar")
                .matches("фу ИЛИ бар");

        Assertions.assertThat(g.rule(BslGrammar.LOGIC_AND_EXPRESSION))
                .matches("foo AND bar")
                .matches("фу И бар");

        Assertions.assertThat(g.rule(BslGrammar.LOGIC_NOT_EXPRESSION))
                .matches("NOT foo")
                .notMatches("NOT NOT foo")
                .matches("NOT (NOT foo)")
                .matches("НЕ фу");
    }

    @Test
    public void relational() {
        Assertions.assertThat(g.rule(BslGrammar.RELATIONAL_EXPRESSION))
                .matches("foo = bar")
                .matches("foo <> bar")
                .matches("foo > bar")
                .matches("foo >= bar")
                .matches("foo < bar")
                .matches("foo <= bar");
    }

    @Test
    public void additive() {
        Assertions.assertThat(g.rule(BslGrammar.ADDITIVE_EXPRESSION))
                .matches("foo + bar")
                .matches("foo - bar");
    }

    @Test
    public void multiplicative() {
        Assertions.assertThat(g.rule(BslGrammar.MULTIPLICATIVE_EXPRESSION))
                .matches("foo * bar")
                .matches("foo / bar")
                .matches("foo % bar");
    }

}
