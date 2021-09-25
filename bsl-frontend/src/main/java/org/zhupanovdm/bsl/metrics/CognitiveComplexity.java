package org.zhupanovdm.bsl.metrics;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

public class CognitiveComplexity implements BslTreeSubscriber {
    @Getter
    private int complexity;

    @Override
    public void init() {
        complexity = 0;
    }
}
