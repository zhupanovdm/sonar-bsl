package org.zhupanovdm.bsl;

import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

public abstract class Check implements BslTreeSubscriber {
    protected ModuleFile context;

    @Override
    public void onEnterFile(ModuleFile context) {
        this.context = context;
    }

    @Override
    public void onLeaveFile(ModuleFile context) {
        this.context = null;
    }

    protected void addIssue(String message, int line, double cost) {
        this.context.addIssue(new Issue(this, message, line, cost));
    }

    protected void addIssue(String message, int line) {
        this.context.addIssue(new Issue(this, message, line, null));
    }

    protected void addIssue(String message) {
        this.context.addIssue(new Issue(this, message, null, null));
    }
}
