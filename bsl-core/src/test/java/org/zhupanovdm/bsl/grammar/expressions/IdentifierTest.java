package org.zhupanovdm.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.IDENTIFIER;

public class IdentifierTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(IDENTIFIER))
                .matches("foo")
                .matches("бар")
                .matches("_")
                .matches("_1")
                .matches("_яz0")
                .matches("while_")
                .matches("пока_")
                .matches("async")
                .notMatches("$")
                .notMatches("foo$")
                .notMatches("while")
                .notMatches("пока")
                .notMatches("123foo")
                .notMatches("123бар");
    }

}
