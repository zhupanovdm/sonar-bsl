package org.zhupanovdm.bsl.tree.module;

import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.MODULE;

public class Module extends BslTree {
    public Module() {
        super(null, MODULE);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitModule(this);
    }

    @Override
    public String toString() {
        return "MODULE {" + getBody().size() + "}";
    }
}