package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class GotoStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.GOTO_STATEMENT)).matches("goto ~label");
    }

}
