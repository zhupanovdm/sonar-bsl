package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.ASSIGNMENT_STATEMENT;

public class AssignmentStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(ASSIGNMENT_STATEMENT))
                .matches("a = 1")
                .matches("a = 1 + 1")
                .matches("a.b.c = e")
                .matches("a.b.c[0] = e")
                .matches("a.b[0].c[0].d = e")
                .notMatches("a() = 1")
                .notMatches("1 = b")
                .notMatches("null = 1")
                .notMatches("a + b = 1")
                .notMatches("new array = 1")
                .notMatches("?(1, 2, 3) = a");
    }

}
