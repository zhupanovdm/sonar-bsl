package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.IF_STATEMENT;

public class IfStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(IF_STATEMENT))
                .matches("if expr1 then endif")
                .matches("if expr1 then else endif")
                .matches("if expr1 then elsif expr2 then endif")
                .matches("if expr1 then elsif expr2 then else endif")
                .matches("if expr1 then elsif expr2 then elsif expr3 then else endif")
                .matches("if expr1 then foo() elsif expr2 then foo() else foo() endif");
    }

}
