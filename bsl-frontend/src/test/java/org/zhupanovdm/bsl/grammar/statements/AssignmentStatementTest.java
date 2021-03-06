package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.ASSIGN_STMT;

public class AssignmentStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(ASSIGN_STMT))
                .matches("a = 1")
                .matches("a = 1 + 1")
                .matches("a.b.c = e")
                .matches("a.b.c[0] = e")
                .matches("a.b[0].c[0].d = e")
                .notMatches("a() = 1")
                .notMatches("1 = b")
                .notMatches("null = 1")
                .notMatches("a + b = 1")
                .notMatches("New Array = 1")
                .notMatches("?(1, 2, 3) = a");
    }

}
