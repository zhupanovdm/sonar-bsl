package org.zhupanovdm.bsl.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.xpath.api.AstNodeXPathQuery;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.Issue;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static org.zhupanovdm.bsl.checks.utils.StringUtils.isNullOrEmpty;

@Rule(key = "XPath")
public class XPathCheck extends BslCheck {
  private static final String DEFAULT_MESSAGE = "The XPath expression matches this piece of code";

  @RuleProperty(
    key = "xpathQuery",
    description = "The XPath query",
    type = "TEXT")
  public String xpathQuery = "";

  @RuleProperty(
    key = "message",
    description = "The issue message",
    defaultValue = "" + DEFAULT_MESSAGE)
  public String message = DEFAULT_MESSAGE;

  private AstNodeXPathQuery<Object> query = null;

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return Collections.emptyList();
  }

  @Override
  public void visitToken(Token token) {
  }

  @Override
  public void visitFile(@Nullable AstNode ast) {
    AstNodeXPathQuery<Object> xpath = query();
    if (xpath != null && ast != null) {
      for (Object object : xpath.selectNodes(ast)) {
        if (object instanceof AstNode) {
          AstNode astNode = (AstNode) object;
          saveIssue(Issue.lineIssue(astNode.getTokenLine(), message));
        } else if (object instanceof Boolean && (Boolean) object) {
          saveIssue(Issue.fileIssue(message));
        }
      }
    }
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

  @CheckForNull
  private AstNodeXPathQuery<Object> query() {
    if (query == null && !isNullOrEmpty(xpathQuery)) {
      try {
        query = AstNodeXPathQuery.create(xpathQuery);
      } catch (RuntimeException e) {
        throw new IllegalStateException("Unable to initialize the XPath engine, perhaps because of an invalid query: " + xpathQuery, e);
      }
    }
    return query;
  }

}
