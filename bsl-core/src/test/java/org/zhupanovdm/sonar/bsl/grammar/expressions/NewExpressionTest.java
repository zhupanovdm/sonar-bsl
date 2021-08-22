package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.NEW_EXPRESSION;

public class NewExpressionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(NEW_EXPRESSION))
                .matches("new Structure")
                .matches("new Structure()")
                .matches("new Structure(\"field\",, 10 + 1,)");
    }

}
