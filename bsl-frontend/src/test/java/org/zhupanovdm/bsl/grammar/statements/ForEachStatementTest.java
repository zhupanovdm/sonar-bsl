package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslGrammar.FOREACH_STMT;

public class ForEachStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(FOREACH_STMT))
                .matches("For each var1 In expr1 Do EndDo")
                .matches("For each var1 In expr1 Do foo(); Break; Continue EndDo");
    }

}
