package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXECUTE_STMT;

public class ExecuteStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(EXECUTE_STMT))
                .matches("Execute(expr1 + \"\")")
                .notMatches("Execute()")
                .notMatches("Execute(a, b)");
    }

}
