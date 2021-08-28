package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.FIELD;

public class FieldTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(FIELD))
                .matches("foo")
                .matches("бар")
                .matches("_")
                .matches("_1")
                .matches("_яz0")
                .matches("while_")
                .matches("пока_")
                .matches("while")
                .matches("пока")
                .notMatches("$")
                .notMatches("foo$")
                .notMatches("123foo")
                .notMatches("123бар");
    }

}
