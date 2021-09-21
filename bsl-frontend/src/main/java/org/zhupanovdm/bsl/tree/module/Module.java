package org.zhupanovdm.bsl.tree.module;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

public class Module extends BslTree {
    public Module() {
        super();
    }

    @Override
    public String toString() {
        return "MODULE {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitModule(this);
    }
}