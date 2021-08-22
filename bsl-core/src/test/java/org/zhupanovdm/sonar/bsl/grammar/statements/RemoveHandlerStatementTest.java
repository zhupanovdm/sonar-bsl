package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.REMOVE_HANDLER_STATEMENT;

public class RemoveHandlerStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(REMOVE_HANDLER_STATEMENT))
                .matches("RemoveHandler expr1.Event, expr2.Handler")
                .matches("УдалитьОбработчик выражение1.Событие, выражение2.Обработчик");
    }

}
