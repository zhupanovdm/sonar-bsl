package org.zhupanovdm.bsl.sonar;

import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.rule.RuleKey;
import org.zhupanovdm.bsl.Check;
import org.zhupanovdm.bsl.checks.CheckList;

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

    @Nullable
    public RuleKey ruleKey(Check check) {
        return checks.ruleKey(check);
    }
}
