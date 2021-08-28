package org.zhupanovdm.bsl.sonar;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;
import org.zhupanovdm.bsl.checks.CheckList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BslRulesDefinition implements RulesDefinition {

    private static final String REPOSITORY_NAME = "SonarAnalyzer";
    private static final String RESOURCE_BASE_PATH = "org/zhupanovdm/bsl/rules";
    private static final Set<String> TEMPLATE_RULE_KEYS = new HashSet<>(Arrays.asList("XPath", "CommentRegularExpression"));

    @Override
    public void define(Context context) {
        NewRepository repository = context
                .createRepository(CheckList.REPOSITORY_KEY, Bsl.KEY)
                .setName(REPOSITORY_NAME);

        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, BslProfile.SONAR_WAY_PROFILE_PATH);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, CheckList.getChecks());

        TEMPLATE_RULE_KEYS.forEach(key -> Objects.requireNonNull(repository.rule(key)).setTemplate(true));

        repository.done();
    }

}
