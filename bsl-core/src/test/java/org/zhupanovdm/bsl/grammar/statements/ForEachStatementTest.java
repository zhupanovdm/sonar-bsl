package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.FOREACH_STATEMENT;

public class ForEachStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(FOREACH_STATEMENT))
                .matches("For each var1 In expr1 Do EndDo")
                .matches("For each var1 In expr1 Do foo(); Break; Continue EndDo");
    }

}
