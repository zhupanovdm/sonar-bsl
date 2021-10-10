package org.zhupanovdm.bsl.tree.module;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.MODULE;

public class ModuleRoot extends BslTree {
    public ModuleRoot() {
        super(null, MODULE);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitModule(this);
    }

    @Override
    public String toString() {
        return "MODULE {" + getBody().size() + "}";
    }
}