package org.zhupanovdm.bsl.utils;

import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.zhupanovdm.bsl.grammar.BslWord;

import java.util.NoSuchElementException;
import java.util.function.Function;

public final class BslGrammarUtils {

    private BslGrammarUtils() {
    }

    public static Object group(LexerlessGrammarBuilder b, BslWord[] words, Function<BslWord, Object> ruleMapper) {
        if (words.length == 0)
            throw new NoSuchElementException("No words are passed");

        Object[] rest = new Object[Math.max(words.length - 2, 0)];
        for (int i = 0; i < words.length; i++) {
            BslWord word = words[i];
            b.rule(word).is(ruleMapper.apply(word));
            if (i > 1)
                rest[i - 2] = word;
        }

        if (words.length == 1)
            return b.sequence(words[0], rest);

        return b.firstOf(words[0], words[1], rest);
    }

    public static Object word(LexerlessGrammarBuilder b, BslWord word) {
        return word(b, word.getValue(), word.getValueRu());
    }

    public static Object word(LexerlessGrammarBuilder b, String value, String valueRu) {
        return b.regexp(caseInsensitiveRegexp(value, valueRu));
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
