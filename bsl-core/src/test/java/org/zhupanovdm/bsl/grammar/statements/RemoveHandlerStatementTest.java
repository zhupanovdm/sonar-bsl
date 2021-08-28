package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.REMOVE_HANDLER_STATEMENT;

public class RemoveHandlerStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(REMOVE_HANDLER_STATEMENT))
                .matches("RemoveHandler expr1.Event, expr2.Handler");
    }

}
