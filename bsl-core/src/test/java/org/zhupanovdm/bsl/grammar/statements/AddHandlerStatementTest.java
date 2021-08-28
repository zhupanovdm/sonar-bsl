package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.ADD_HANDLER_STATEMENT;

public class AddHandlerStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(ADD_HANDLER_STATEMENT))
                .matches("AddHandler expr1.Event, expr2.Handler");
    }

}
