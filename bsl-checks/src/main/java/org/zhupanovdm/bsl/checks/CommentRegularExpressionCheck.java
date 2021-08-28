package org.zhupanovdm.bsl.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.Issue;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
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
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return Collections.emptyList();
  }

  @Override
  public void visitToken(Token token) {
    Pattern regexp = pattern();
    if (regexp != null) {
      for (Trivia trivia : token.getTrivia()) {
        if (trivia.isComment() && regexp.matcher(trivia.getToken().getOriginalValue()).matches()) {
          saveIssue(Issue.lineIssue(trivia.getToken().getLine(), message));
        }
      }
    }
  }

  @Override
  public void visitFile(@Nullable AstNode ast) {
  }

  @Override
  public void leaveFile(@Nullable AstNode ast) {
  }

  @Override
  public void visitNode(AstNode ast) {
  }

  @Override
  public void leaveNode(AstNode ast) {
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
