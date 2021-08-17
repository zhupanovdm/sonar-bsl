package org.zhupanovdm.sonar.bsl.grammar.expressions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class IdentifierTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.IDENTIFIER))
                .matches("_")
                .matches("_1")
                .matches("_яz")
                .matches("identifier")
                .matches("идентификатор")
                .matches("пока123")
                .matches("while123")
                .notMatches("for")
                .notMatches("For")
                .notMatches("для")
                .notMatches("Для")
                .notMatches("123")
                .notMatches("123qwer")
                .notMatches("123йцук");
    }

}
