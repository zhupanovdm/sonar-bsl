package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class ForStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.FOR_STATEMENT))
                .matches("For var1 = expr1 To expr2 Do EndDo")
                .matches("For var1 = expr1 To expr2 Do Break; Continue EndDo")
                .matches("Для перем1 = выражение1 По выражение1 Цикл КонецЦикла");
    }

}
