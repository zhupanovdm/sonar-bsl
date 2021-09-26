package org.zhupanovdm.bsl.sonar;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;
import org.zhupanovdm.bsl.checks.CheckList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BslRuleRepository implements RulesDefinition {
    private static final String REPOSITORY_NAME = "SonarAnalyzer";

    static final String RESOURCE_FOLDER = "org/zhupanovdm/bsl/rules";

    private static final Set<String> TEMPLATE_RULE_KEYS = new HashSet<>(Collections.singletonList("CommentRegularExpression"));

    @Override
    public void define(Context context) {
        NewRepository repository = context
                .createRepository(CheckList.REPOSITORY_KEY, Bsl.KEY)
                .setName(REPOSITORY_NAME);

        RuleMetadataLoader loader = new RuleMetadataLoader(RESOURCE_FOLDER, BslProfile.PROFILE_LOCATION);
        loader.addRulesByAnnotatedClass(repository, CheckList.getChecks());

        repository.rules().stream()
                .filter(rule -> TEMPLATE_RULE_KEYS.contains(rule.key()))
                .forEach(rule -> rule.setTemplate(true));

        repository.done();
    }
}
