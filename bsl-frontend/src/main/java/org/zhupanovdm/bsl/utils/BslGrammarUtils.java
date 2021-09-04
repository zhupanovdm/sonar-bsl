package org.zhupanovdm.bsl.utils;

import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.zhupanovdm.bsl.api.BslWord;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class BslGrammarUtils {

    private BslGrammarUtils() {
    }

    public static Object wordGroup(LexerlessGrammarBuilder b, BslWord[] words, Function<BslWord, Object> ruleMapper) {
        if (words.length == 0) {
            throw new NoSuchElementException("No words are passed");
        }

        for (BslWord word : words) {
            b.rule(word).is(ruleMapper.apply(word));
        }

        if (words.length == 1) {
            return words[0];
        } else if (words.length == 2) {
            return b.firstOf(words[0], words[1]);
        }

        return b.firstOf(words[0], words[1], (Object[]) Arrays.copyOfRange(words, 2, words.length));
    }

    public static Object word(LexerlessGrammarBuilder b, BslWord w) {
        return b.regexp(caseInsensitiveRegexp(w.getValue(), w.getValueAlt()));
    }

    private static String caseInsensitiveRegexp(String value, String valueRu) {
        StringBuilder builder = new StringBuilder("(?i)");
        builder.append(value);

        if (valueRu != null) {
            String lowerRu = valueRu.toLowerCase();
            String upperRu = valueRu.toUpperCase();

            builder.append('|');

            for (int i = 0; i < lowerRu.length(); i++) {
                builder.append('[');
                builder.append(lowerRu.charAt(i));
                builder.append(upperRu.charAt(i));
                builder.append(']');
            }
        }

        return builder.toString();
    }

}
