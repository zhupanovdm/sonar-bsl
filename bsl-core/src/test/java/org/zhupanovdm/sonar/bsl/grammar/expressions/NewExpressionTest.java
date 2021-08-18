package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class NewExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.NEW_EXPRESSION))
                .matches("New Structure")
                .matches("New Structure()")
                .matches("New Structure(\"field\",, 10 + 1,)")
                .matches("New Array(10)")
                .matches("Новый Структура");
    }

}
