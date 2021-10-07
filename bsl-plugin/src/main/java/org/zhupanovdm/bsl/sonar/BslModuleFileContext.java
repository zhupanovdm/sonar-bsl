package org.zhupanovdm.bsl.sonar;

import lombok.Data;
import lombok.ToString;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.ModuleFile;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.module.Module;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@ToString(of = "file")
public class BslModuleFileContext implements ModuleFile {
    private final SensorContext context;
    private final BslChecks checks;
    private final InputFile file;
    private final Charset charset;
    private final String contents;
    private final Module entry;

    public BslModuleFileContext(SensorContext context, BslChecks checks, @Nonnull InputFile file) {
        this.context = context;
        this.checks = checks;
        this.file = file;
        this.charset = file.charset();
        try {
            contents = file.contents();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read content of input file " + file, e);
        }
        entry = BslTreeCreator.module(BslParser.create(charset).parse(contents));
    }

    @Override
    public void addIssue(Issue checkIssue) {
        RuleKey ruleKey = Objects.requireNonNull(checks.ruleKey(checkIssue.getCheck()), "Rule key for check is required");

        NewIssue issue = context.newIssue();
        NewIssueLocation location = issue.newLocation().on(file).message(checkIssue.getMessage());
        checkIssue.getLine().ifPresent(line -> location.at(file.selectLine(line)));
        checkIssue.getCost().ifPresent(issue::gap);
        issue.at(location).forRule(ruleKey).save();
    }

    @Override
    public List<Issue> getIssues() {
        return Collections.emptyList();
    }
}