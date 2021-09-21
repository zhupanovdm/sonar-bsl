package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstAndTokenVisitor;

import java.util.function.BiConsumer;

public abstract class BslCheck implements AstAndTokenVisitor {
    private BiConsumer<BslCheck, Issue> issueConsumer;

    public void setIssueConsumer(BiConsumer<BslCheck, Issue> issueConsumer) {
        this.issueConsumer = issueConsumer;
    }

    protected void saveIssue(Issue issue) {
        issueConsumer.accept(this, issue);
    }
}
