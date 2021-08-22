package org.zhupanovdm.sonar.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.sonar.bsl.grammar.BslGrammar.PREPROCESSOR_CONDITION;

public class BslPreprocessorConditionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(PREPROCESSOR_CONDITION))
                .matches("server")
                .matches("not server")
                .matches("server and client")
                .matches("server or client")
                .matches("not server and client")
                .matches("not server and not client")
                .notMatches("not not server")
                .notMatches("abc")
                .notMatches("1 + 1")
                .notMatches("");
    }

}
