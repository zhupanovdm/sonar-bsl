package org.zhupanovdm.bsl.grammar.literals;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslGrammar.NUMBER;

public class PrimitiveNumberTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(NUMBER))
                .matches("123")
                .matches("-123")
                .matches("+123")
                .matches("123.01230");
    }

}
