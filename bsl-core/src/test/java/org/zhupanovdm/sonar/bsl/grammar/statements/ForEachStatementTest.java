package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class ForEachStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.FOREACH_STATEMENT))
                .matches("For each var1 In expr1 Do EndDo")
                .matches("For each var1 In expr1 Do Break; Continue EndDo")
                .matches("Для каждого перем1 Из выражение1 Цикл КонецЦикла");
    }

}
