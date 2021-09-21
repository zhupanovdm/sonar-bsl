package org.zhupanovdm.bsl.utils;

import org.junit.Test;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class BslGrammarUtilsTest {

    @Test
    public void word() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
        b.rule(SampleGrammar.WORD).is(BslGrammarUtils.word(b, "Word", "Слово"));
        LexerlessGrammar g = b.build();

        assertThat(g.rule(SampleGrammar.WORD))
                .matches("word")
                .matches("WorD")
                .matches("слово")
                .matches("СловО")
                .notMatches("word1")
                .notMatches("w ord")
                .notMatches("WordСлово");
    }

    private enum SampleGrammar implements GrammarRuleKey {
        WORD
    }

}