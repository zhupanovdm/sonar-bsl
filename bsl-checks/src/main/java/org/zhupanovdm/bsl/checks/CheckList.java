package org.zhupanovdm.bsl.checks;

import java.util.Arrays;
import java.util.List;

public final class CheckList {

    public static final String REPOSITORY_KEY = "bsl";

    private CheckList() {
    }

    public static List<Class<?>> getChecks() {
        return Arrays.asList(
                XPathCheck.class,
                CommentRegularExpressionCheck.class
        );
    }

}
