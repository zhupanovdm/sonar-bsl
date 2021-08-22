package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.PRIMITIVE;

public class PrimitiveTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void primitive() {
        assertThat(g.rule(PRIMITIVE))
                .matches("undefined")
                .matches("null")
                .matches("true")
                .matches("false")
                .matches("\"\"")
                .matches("123")
                .matches("'00010101'")
                .notMatches("1 + 1")
                .notMatches("");
    }

}
