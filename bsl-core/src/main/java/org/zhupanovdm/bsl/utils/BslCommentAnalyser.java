package org.zhupanovdm.bsl.utils;

public final class BslCommentAnalyser {
    private static final String NOSONAR = "NOSONAR";

    private BslCommentAnalyser() {
    }

    public static boolean isBlank(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetterOrDigit(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getContents(String comment) {
        return comment.substring(2);
    }

    public static boolean isNoSonar(String content) {
        return content.contains(NOSONAR);
    }

}
