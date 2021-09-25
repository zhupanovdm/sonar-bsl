package org.zhupanovdm.bsl.tree.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasCondition;

import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.tree.BslTree.Type.PREPROCESSOR;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreprocessorIf extends BslTree implements HasCondition {
    private BslTree condition;
    private final List<PreprocessorElsif> elsIfBranches = new LinkedList<>();

    public PreprocessorIf(BslTree parent) {
        super(parent, PREPROCESSOR);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitPreprocessorIf(this);
    }

    @Override
    public String toString() {
        return "#If " + condition + " Then {" + getBody().size() + '}';
    }
}