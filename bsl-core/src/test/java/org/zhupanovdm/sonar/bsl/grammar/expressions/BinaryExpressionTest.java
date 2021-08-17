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
                .matches("foo or bar")
                .matches("фу или бар");

        Assertions.assertThat(g.rule(BslGrammar.LOGIC_AND_EXPRESSION))
                .matches("foo and bar")
                .matches("фу и бар");

        Assertions.assertThat(g.rule(BslGrammar.LOGIC_NOT_EXPRESSION))
                .matches("not foo")
                .matches("не фу")
                .notMatches("not not foo")
                .matches("not (not foo)");
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
