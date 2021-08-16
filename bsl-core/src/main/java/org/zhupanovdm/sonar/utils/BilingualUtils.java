package org.zhupanovdm.sonar.utils;

import org.zhupanovdm.sonar.bsl.grammar.BilingualWord;

public final class BilingualUtils {

    private BilingualUtils() {
    }

    public static String[] wordValues(BilingualWord[] words) {
        String[] keywordsValue = new String[words.length * 2];
        for (int i = 0; i < words.length; i++) {
            keywordsValue[2 * i] = words[i].getValue();
            keywordsValue[2 * i + 1] = words[i].getValueRu();
        }
        return keywordsValue;
    }

    public static String caseInsensitiveRegexp(String value, String valueRu) {
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
