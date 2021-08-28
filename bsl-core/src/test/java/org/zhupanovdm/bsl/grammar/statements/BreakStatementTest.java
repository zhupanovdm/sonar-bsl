package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.BREAK_STATEMENT;

public class BreakStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BREAK_STATEMENT))
                .matches("Break");
    }

}
