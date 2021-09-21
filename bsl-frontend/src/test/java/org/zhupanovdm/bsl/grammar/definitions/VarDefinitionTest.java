package org.zhupanovdm.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.api.BslGrammar.VARIABLE;
import static org.zhupanovdm.bsl.api.BslGrammar.VAR_DEF;

public class VarDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void definition() {
        assertThat(g.rule(VAR_DEF))
                .matches("Var a;")
                .matches("Var a, b, c;")
                .matches("&AtServer Var a, b, c;")
                .notMatches("Var a")
                .notMatches("Var a b;");
    }

    @Test
    public void variable() {
        assertThat(g.rule(VARIABLE))
                .matches("a")
                .matches("a Export")
                .notMatches("for")
                .notMatches("1")
                .notMatches("");
    }

}
