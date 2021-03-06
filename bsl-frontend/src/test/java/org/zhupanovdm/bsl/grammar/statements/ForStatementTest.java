package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.FOR_STMT;

public class ForStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(FOR_STMT))
                .matches("For var1 = expr1 To expr2 Do EndDo")
                .matches("For var1 = expr1 To expr2 Do foo(); Break; Continue EndDo");
    }

}
