package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class AddHandlerStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.ADD_HANDLER_STATEMENT))
                .matches("AddHandler expr1.Event, expr2.Handler")
                .matches("ДобавитьОбработчик выражение1.Событие, выражение2.Обработчик");
    }

}
