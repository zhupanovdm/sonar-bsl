package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.IF_STATEMENT;

public class IfStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(IF_STATEMENT))
                .matches("If expr1 Then EndIf")
                .matches("If expr1 Then Else EndIf")
                .matches("If expr1 Then ElsIf expr2 Then EndIf")
                .matches("If expr1 Then ElsIf expr2 Then Else EndIf")
                .matches("If expr1 Then ElsIf expr2 Then ElsIf expr3 Then Else EndIf")
                .matches("If expr1 Then foo() ElsIf expr2 Then foo() Else foo() EndIf");
    }

}
