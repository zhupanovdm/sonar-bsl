package org.zhupanovdm.bsl.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslWord;

import static org.mockito.Mockito.when;
import static org.sonar.sslr.tests.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BslGrammarUtilsTest {

    @Mock
    BslWord word;

    @Before
    public void setUp() {
        when(word.getValue()).thenReturn("Word");
        when(word.getValueAlt()).thenReturn("Слово");
    }

    @Test
    public void word() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
        b.rule(SampleGrammar.WORD).is(BslGrammarUtils.word(b, word));
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