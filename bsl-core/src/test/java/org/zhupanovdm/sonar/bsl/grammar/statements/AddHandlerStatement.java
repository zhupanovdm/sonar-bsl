package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class AddHandlerStatement {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.ADD_HANDLER_STATEMENT))
                .matches("AddHandler expr1.Event, expr2.Handler")
                .matches("ДобавитьОбработчик выражение1.Событие, выражение2.Обработчик");
    }

}
