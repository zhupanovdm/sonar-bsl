package org.zhupanovdm.bsl.checks.utils;

import javax.annotation.Nullable;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }

}