package org.zhupanovdm.sonar.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;

public class IdentifierTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        Assertions.assertThat(g.rule(BslGrammar.IDENTIFIER))
                .matches("_")
                .matches("identifier")
                .matches("идентификатор")
                .notMatches("for")
                .notMatches("для")
                .notMatches("Для")
                .matches("пока123")
                .matches("while123");
    }

}
