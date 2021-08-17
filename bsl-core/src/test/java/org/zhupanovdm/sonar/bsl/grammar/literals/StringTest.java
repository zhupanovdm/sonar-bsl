package org.zhupanovdm.sonar.bsl.grammar.literals;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.tests.Assertions;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

public class StringTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void basic() {
        Assertions.assertThat(g.rule(BslGrammar.STRING))
            .matches("\"foo\"")
            .matches("\"\"")
            .matches("\"\"\"\"")
            .matches("\"foo\"\"\"")
            .matches("\"|\"")
            .notMatches("\"\"\"")
            .notMatches("\"");
    }

    @Test
    public void compound() {
        Assertions.assertThat(g.rule(BslGrammar.STRING))
            .matches("\"foo\" \"bar\"")
            .matches("\"foo\"\n\"bar\"")
            .matches("\"\" \"\"")
            .matches("\"\" \"\n|\" \"\" \"\n|\"");
    }

    @Test
    public void carryover() {
        Assertions.assertThat(g.rule(BslGrammar.STRING))
            .matches("\"\n|\"")
            .matches("\"foo\n|bar\"")
            .matches("\"\"\"\n|\"\"\"");
    }

}
