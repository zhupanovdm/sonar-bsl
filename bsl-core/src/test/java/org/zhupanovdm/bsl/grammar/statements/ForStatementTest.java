package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.FOR_STATEMENT;

public class ForStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(FOR_STATEMENT))
                .matches("For var1 = expr1 To expr2 Do EndDo")
                .matches("For var1 = expr1 To expr2 Do foo(); Break; Continue EndDo");
    }

}
