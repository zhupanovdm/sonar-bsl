package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.REMOVE_HANDLER_STMT;

public class RemoveHandlerStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(REMOVE_HANDLER_STMT))
                .matches("RemoveHandler expr1.Event, expr2.Handler");
    }

}
