package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class PrimitiveTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void primitive() {
        Assertions.assertThat(g.rule(BslGrammar.PRIMITIVE))
                .matches("undefined")
                .matches("null")
                .matches("true")
                .matches("false")
                .matches("\"\"")
                .matches("123")
                .matches("'00010101'")
                .notMatches("1 + 1");
    }

}
