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
                .matches("while expr1 do enddo")
                .matches("while expr1 do foo(); break; continue enddo");
    }

}
