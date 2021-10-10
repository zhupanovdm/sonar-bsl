package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.MultiFileVerifier;
import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.Issue;

import javax.annotation.Nonnull;

class VerifyingSampleModuleContext extends AbstractModuleContext {
    private final MultiFileVerifier verifier;

    public VerifyingSampleModuleContext(SampleModuleFile sample) {
        super(sample);
        this.verifier = MultiFileVerifier.create(sample.getPath(), sample.getCharset());
    }

    @Override
    public SampleModuleFile getFile() {
        return (SampleModuleFile) file;
    }

    public MultiFileVerifier getVerifier() {
        return verifier;
    }

    @Override
    public void addIssue(@Nonnull Issue issue) {
        MultiFileVerifier.IssueBuilder builder = verifier.reportIssue(getFile().getPath(), issue.getMessage());
        issue.getLine().ifPresentOrElse(builder::onLine, builder::onFile);
    }
}
