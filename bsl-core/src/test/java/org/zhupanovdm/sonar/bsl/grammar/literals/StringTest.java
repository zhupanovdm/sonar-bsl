package org.zhupanovdm.sonar.bsl.grammar.literals;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class StringTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void basic() {
        assertThat(g.rule(BslGrammar.STRING))
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
        assertThat(g.rule(BslGrammar.STRING))
                .matches("\"foo\" \"bar\"")
                .matches("\"foo\"\n\"bar\"")
                .matches("\"\" \"\"")
                .matches("\"\" \"\n|\" \"\" \"\n|\"");
    }

    @Test
    public void carryover() {
        assertThat(g.rule(BslGrammar.STRING))
                .matches("\"\n|\"")
                .matches("\"foo\n|bar\"")
                .matches("\"\"\"\n|\"\"\"");
    }

}
