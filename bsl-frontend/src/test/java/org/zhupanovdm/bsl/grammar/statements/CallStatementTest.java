package org.zhupanovdm.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.CALL_STMT;

public class CallStatementTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(CALL_STMT))
                .matches("a()")
                .matches("a(,1 + 1,, a,)")
                .matches("a.b()")
                .matches("a().b()")
                .matches("a[0].b()");
    }

}
