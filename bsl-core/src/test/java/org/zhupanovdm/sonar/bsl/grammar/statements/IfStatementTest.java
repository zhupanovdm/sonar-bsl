package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class IfStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.IF_STATEMENT))
                .matches("if expr1 then endif")
                .matches("if expr1 then else endif")
                .matches("if expr1 then elsif expr2 then endif")
                .matches("if expr1 then elsif expr2 then else endif")
                .matches("if expr1 then elsif expr2 then elsif expr3 then else endif");
    }

}