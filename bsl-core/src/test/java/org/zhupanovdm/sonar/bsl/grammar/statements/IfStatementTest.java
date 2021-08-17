package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class IfStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.IF_STATEMENT))
                .matches("If expr1 Then EndIf")
                .matches("If expr1 Then Else EndIf")
                .matches("If expr1 Then ElsIf expr2 Then EndIf")
                .matches("If expr1 Then ElsIf expr2 Then Else EndIf")
                .matches("If expr1 Then ElsIf expr2 Then ElsIf expr3 Then Else EndIf")
                .matches("Если выражение1 Тогда ИначеЕсли выражение2 Тогда Иначе КонецЕсли");
    }

}
