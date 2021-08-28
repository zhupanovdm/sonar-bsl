package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.NEW_EXPRESSION;

public class NewExpressionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(NEW_EXPRESSION))
                .matches("New Structure")
                .matches("New Structure()")
                .matches("New Structure(\"field\",, 10 + 1,)");
    }

}
