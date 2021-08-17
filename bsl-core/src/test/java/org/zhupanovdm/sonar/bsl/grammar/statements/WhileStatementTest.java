package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class WhileStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.WHILE_STATEMENT))
                .matches("While expr1 Do EndDo")
                .matches("While expr1 Do Break; Continue EndDo")
                .matches("Пока выражение1 Цикл КонецЦикла");
    }

}