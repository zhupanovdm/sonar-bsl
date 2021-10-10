package org.zhupanovdm.bsl.sonar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.Issue;

import javax.annotation.Nonnull;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class BslModuleFileContext extends AbstractModuleContext {
    private final SensorContext sensor;
    private final BslChecks checks;

    public BslModuleFileContext(BslModuleFile moduleFile, SensorContext sensor, BslChecks checks) {
        super(moduleFile);
        this.sensor = sensor;
        this.checks = checks;
    }

    @Override
    public BslModuleFile getFile() {
        return (BslModuleFile) file;
    }

    @Override
    public void addIssue(@Nonnull Issue checkIssue) {
        RuleKey ruleKey = Objects.requireNonNull(checks.ruleKey(checkIssue.getCheck()), "Rule key for check is required");

        InputFile inputFile = getFile().getInput();

        NewIssue issue = sensor.newIssue();
        NewIssueLocation location = issue.newLocation().on(inputFile).message(checkIssue.getMessage());

        checkIssue.getLine().ifPresent(line -> location.at(inputFile.selectLine(line)));
        checkIssue.getCost().ifPresent(issue::gap);
        issue.at(location).forRule(ruleKey).save();
    }
}