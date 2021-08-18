package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class ExecuteStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(BslGrammar.EXECUTE_STATEMENT))
                .matches("Execute(expr1 + \"\")")
                .matches("Выполнить(выражение1 + \"\")")
                .notMatches("Execute()")
                .notMatches("Execute(a, b)");
    }

}
