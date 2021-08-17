package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class ExecuteStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.EXECUTE_STATEMENT))
                .matches("Execute(expr1 + \"\")")
                .matches("Выполнить(выражение1 + \"\")")
                .notMatches("Execute()")
                .notMatches("Execute(a, b)");
    }

}
