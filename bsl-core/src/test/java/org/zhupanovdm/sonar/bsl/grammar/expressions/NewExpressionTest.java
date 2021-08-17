package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class NewExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.NEW_EXPRESSION))
                .matches("new Structure")
                .matches("new Structure()")
                .matches("new Structure(\"field\",, 10 + 1,)")
                .matches("new Array(10)")
                .matches("Новый Структура");
    }

}
