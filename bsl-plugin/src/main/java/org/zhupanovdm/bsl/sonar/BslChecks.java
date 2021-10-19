package org.zhupanovdm.bsl.sonar;

import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.rule.RuleKey;
import org.zhupanovdm.bsl.Check;
import org.zhupanovdm.bsl.checks.CheckList;
import org.zhupanovdm.bsl.tree.BslTreeSubscribers;

import javax.annotation.Nullable;
import java.util.Collection;

public class BslChecks {
    private final Checks<Check> checks;

    public BslChecks(CheckFactory checkFactory) {
        this.checks = checkFactory
                .<Check>create(CheckList.REPOSITORY_KEY)
                .addAnnotatedChecks(CheckList.getChecks());
    }

    public Collection<Check> all() {
        return checks.all();
    }

    public BslTreeSubscribers subscribers() {
        BslTreeSubscribers subscribers = new BslTreeSubscribers();
        subscribers.add(all());
        return subscribers;
    }

    @Nullable
    public RuleKey ruleKey(Check check) {
        return checks.ruleKey(check);
    }
}
