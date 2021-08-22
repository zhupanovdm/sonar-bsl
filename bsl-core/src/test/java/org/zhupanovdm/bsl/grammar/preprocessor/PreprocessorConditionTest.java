package org.zhupanovdm.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.PP_CONDITION;

public class PreprocessorConditionTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void test() {
        assertThat(g.rule(PP_CONDITION))
                .matches("server")
                .matches("not server")
                .matches("server and client")
                .matches("server or client")
                .matches("not server and client")
                .matches("not server and not client")
                .notMatches("not not server")
                .notMatches("1 + 1")
                .notMatches("");
    }

}
