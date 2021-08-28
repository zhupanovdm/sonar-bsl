package org.zhupanovdm.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.BslGrammar.LABEL;
import static org.zhupanovdm.bsl.BslGrammar.LABEL_DEF;

public class LabelDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void label() {
        assertThat(g.rule(LABEL))
                .matches("~label")
                .notMatches("~ label")
                .notMatches("~\nlabel");
    }

    @Test
    public void definition() {
        assertThat(g.rule(LABEL_DEF))
                .matches("~label:")
                .matches("~label\n:");
    }

}
