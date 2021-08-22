package org.zhupanovdm.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.VARIABLE;
import static org.zhupanovdm.bsl.grammar.BslGrammar.VAR_DEF;

public class VarDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void definition() {
        assertThat(g.rule(VAR_DEF))
                .matches("var a;")
                .matches("var a, b, c;")
                .matches("&AtServer var a, b, c;")
                .notMatches("var a")
                .notMatches("var a b;");
    }

    @Test
    public void variable() {
        assertThat(g.rule(VARIABLE))
                .matches("a")
                .matches("a export")
                .notMatches("for")
                .notMatches("1")
                .notMatches("");
    }

}
