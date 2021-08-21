package org.zhupanovdm.sonar.bsl.grammar.definitions;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.VARIABLE;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.VAR_DEFINITION;

public class VarDefinitionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void definition() {
        assertThat(g.rule(VAR_DEFINITION))
                .matches("var a;")
                .matches("var a export;")
                .matches("var a, b, c;")
                .matches("&AtServer var a, b export, c;")
                .matches("&AtServer\nvar\na\n,\nb export\n,\nc\n;")
                .notMatches("var a")
                .notMatches("var a b;");
    }

    @Test
    public void variable() {
        assertThat(g.rule(VARIABLE))
                .matches("a")
                .matches("a export")
                .matches("a\nexport")
                .notMatches("for")
                .notMatches("1")
                .notMatches("");
    }

}
