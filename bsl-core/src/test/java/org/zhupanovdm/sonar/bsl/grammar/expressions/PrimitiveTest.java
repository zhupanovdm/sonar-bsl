package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class PrimitiveTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void primitive() {
        assertThat(g.rule(BslGrammar.PRIMITIVE))
                .matches("Undefined")
                .matches("Неопределено")
                .matches("NULL")
                .matches("True")
                .matches("Истина")
                .matches("False")
                .matches("Ложь")
                .matches("\"\"")
                .matches("123")
                .matches("'00010101'")
                .notMatches("1 + 1");
    }

}
