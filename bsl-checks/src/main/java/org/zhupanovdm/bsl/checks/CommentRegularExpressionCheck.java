package org.zhupanovdm.bsl.checks;

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTrivia;

import java.util.Objects;
import java.util.regex.Pattern;

import static org.zhupanovdm.bsl.checks.utils.StringUtils.isNullOrEmpty;

@Rule(key = "CommentRegularExpression")
public class CommentRegularExpressionCheck extends BslCheck {
  private static final String DEFAULT_MESSAGE = "The regular expression matches this comment.";

  @RuleProperty(
    key = "regularExpression",
    description = "The regular expression")
  public String regularExpression = "";

  @RuleProperty(
    key = "message",
    description = "The issue message",
    defaultValue = "" + DEFAULT_MESSAGE)
  public String message = DEFAULT_MESSAGE;

  private Pattern pattern = null;

  @Override
  public void onEnterNode(BslTree node) {
    for (BslToken token : node.getTokens()) {
      visitToken(token);
    }
  }

  private void visitToken(BslToken token) {
    Pattern regexp = pattern();
    if (regexp != null) {
      for (BslTrivia comment : token.getComments()) {
        if (regexp.matcher(comment.getValue()).matches()) {
          saveIssue(Issue.lineIssue(comment.getTokens().get(0).getLine(), message));
        }
      }
    }
  }

  private Pattern pattern() {
    if (pattern == null) {
      Objects.requireNonNull(regularExpression, "getRegularExpression() should not return null");
      if (!isNullOrEmpty(regularExpression)) {
        try {
          pattern = Pattern.compile(regularExpression, Pattern.DOTALL);
        } catch (RuntimeException e) {
          throw new IllegalStateException("Unable to compile regular expression: " + regularExpression, e);
        }
      }
    }
    return pattern;
  }
}
