package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class TryStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.TRY_STATEMENT))
                .matches("Try foo() Except EndTry")
                .matches("Try foo() Except Raise \"\" EndTry")
                .matches("Попытка фуу() Исключение КонецПопытки");
    }

}
