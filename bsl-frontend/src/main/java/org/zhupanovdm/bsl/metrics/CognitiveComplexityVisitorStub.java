package org.zhupanovdm.bsl.metrics;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class CognitiveComplexityVisitorStub extends BslTreeVisitor {
    private int complexity;

    public static int complexity(BslTree tree) {
        CognitiveComplexityVisitorStub visitor = new CognitiveComplexityVisitorStub();
        visitor.scan(tree);
        return visitor.complexity;
    }
}
