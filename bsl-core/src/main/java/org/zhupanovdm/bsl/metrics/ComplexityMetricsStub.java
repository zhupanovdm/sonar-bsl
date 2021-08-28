package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;

public class ComplexityMetricsStub {
    public static int complexity(AstNode tree) {
        // TODO: implementation
        return tree.getDescendants().size();
    }
}