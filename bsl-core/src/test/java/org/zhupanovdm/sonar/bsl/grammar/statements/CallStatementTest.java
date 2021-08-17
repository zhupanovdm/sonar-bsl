package org.zhupanovdm.sonar.bsl.grammar.statements;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class CallStatementTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.CALL_STATEMENT))
                .matches("a()")
                .matches("a(,1 + 1,, a,)")
                .matches("a.b()")
                .matches("a().b()")
                .matches("a[0].b()");
    }

}
