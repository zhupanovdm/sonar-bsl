package org.zhupanovdm.bsl;

import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.util.function.BiConsumer;

public abstract class BslCheck implements BslTreeSubscriber {
    private BiConsumer<BslCheck, Issue> issueConsumer;

    public void setIssueConsumer(BiConsumer<BslCheck, Issue> issueConsumer) {
        this.issueConsumer = issueConsumer;
    }

    protected void saveIssue(Issue issue) {
        issueConsumer.accept(this, issue);
    }
}
