package org.zhupanovdm.bsl.grammar.preprocessor;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.PP_CONDITION;

public class PreprocessorConditionTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void test() {
        assertThat(g.rule(PP_CONDITION))
                .matches("Server")
                .matches("Not Server")
                .matches("Server And Client")
                .matches("Server Or Client")
                .matches("Server Or Client And WebApplication")
                .matches("Not Server And Client")
                .matches("Not Server And Not Client")
                .notMatches("Not Not Server")
                .notMatches("1 + 1")
                .notMatches("");
    }

}
