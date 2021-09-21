package org.zhupanovdm.bsl.utils;

import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public final class BslGrammarUtils {
    private BslGrammarUtils() {
    }

    public static Object word(LexerlessGrammarBuilder b, String value, String valueAlt) {
        return b.regexp(caseInsensitiveRegexp(value, valueAlt));
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
