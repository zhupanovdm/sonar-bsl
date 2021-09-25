package org.zhupanovdm.bsl.tree.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.HasCondition;

import static org.zhupanovdm.bsl.tree.BslTree.Type.PREPROCESSOR;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreprocessorElsif extends BslTree implements HasCondition {
    private BslTree condition;

    public PreprocessorElsif(PreprocessorIf parent) {
        super(parent, PREPROCESSOR);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitPreprocessorElsif(this);
    }

    @Override
    public String toString() {
        return "#ElsIf " + condition + " Then {" + getBody().size() + '}';
    }
}
