package org.zhupanovdm.bsl.utils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

public final class StringUtils {
    private StringUtils() {
    }

    public static <T> StringBuilder collectionToString(StringBuilder builder, Collection<T> collection, Function<T, String> mapper, String delimiter) {
        boolean first = true;
        for (T item : collection) {
            if (first) {
                first = false;
            } else {
                builder.append(delimiter);
            }
            builder.append(mapper.apply(item));
        }
        return builder;
    }

    public static <T> String collectionToString(Collection<T> collection, Function<T, String> mapper, String delimiter) {
        return collectionToString(new StringBuilder(), collection, mapper, delimiter).toString();
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }

    public static String caseInsensitiveBilingualRegexpString(String value, String valueAlt) {
        StringBuilder builder = new StringBuilder("(?i)");
        builder.append(value);

        if (valueAlt != null) {
            String lowerRu = valueAlt.toLowerCase();
            String upperRu = valueAlt.toUpperCase();

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
