package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class WhileStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.WHILE_STATEMENT))
                .matches("While expr1 Do EndDo")
                .matches("While expr1 Do Break; Continue EndDo")
                .matches("Пока выражение1 Цикл КонецЦикла");
    }

}
