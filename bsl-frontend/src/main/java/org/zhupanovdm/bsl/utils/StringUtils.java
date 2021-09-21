package org.zhupanovdm.bsl.utils;

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
}
