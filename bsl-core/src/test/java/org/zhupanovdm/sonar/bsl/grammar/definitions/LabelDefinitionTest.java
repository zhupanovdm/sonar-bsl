package org.zhupanovdm.sonar.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class LabelDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void label() {
        Assertions.assertThat(g.rule(BslGrammar.LABEL))
                .matches("~label")
                .notMatches("~ label")
                .notMatches("~\nlabel");
    }

    @Test
    public void definition() {
        Assertions.assertThat(g.rule(BslGrammar.LABEL_DEFINITION))
                .matches("~label:")
                .matches("~label\n:");
    }

}
