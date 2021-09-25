package org.zhupanovdm.bsl.checks;

import java.util.Collections;
import java.util.List;

public final class CheckList {

    public static final String REPOSITORY_KEY = "bsl";

    private CheckList() {
    }

    public static List<Class<?>> getChecks() {
        return Collections.singletonList(
                CommentRegularExpressionCheck.class
        );
    }

}
