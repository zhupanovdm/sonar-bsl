package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import org.zhupanovdm.bsl.BslAstVisitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sonar.sslr.api.GenericTokenType.EOF;

public class LinesMetrics {
    private static final String NOSONAR_FLAG = "NOSONAR";

    private final Set<Integer> linesOfCode = new HashSet<>();
    private final Set<Integer> linesOfComments = new HashSet<>();
    private final Set<Integer> linesNoSonar = new HashSet<>();

    public LinesMetrics(AstNode tree) {
        new TokenVisitor().scan(tree);
    }

    public Set<Integer> getLinesOfCode() {
        return Collections.unmodifiableSet(linesOfCode);
    }

    public Set<Integer> getLinesOfComments() {
        return Collections.unmodifiableSet(linesOfComments);
    }

    public Set<Integer> getLinesNoSonar() {
        return Collections.unmodifiableSet(linesNoSonar);
    }

    private class TokenVisitor extends BslAstVisitor {
        @Override
        public List<AstNodeType> subscribedTo() {
            return Collections.emptyList();
        }

        @Override
        public void visitToken(Token token) {
            if (!token.getType().equals(EOF))
                linesOfCode.add(token.getLine());

            for (Trivia trivia : token.getTrivia()) {
                if (!trivia.isComment())
                    continue;
                String content = trivia.getToken().getOriginalValue().substring(2);
                int line = trivia.getToken().getLine();
                if (content.contains(NOSONAR_FLAG)) {
                    linesOfComments.remove(line);
                    linesNoSonar.add(line);
                } else if (!isBlank(content)) {
                    linesOfComments.add(line);
                }
            }
        }

        private boolean isBlank(String line) {
            for (int i = 0; i < line.length(); i++)
                if (Character.isLetterOrDigit(line.charAt(i)))
                    return false;
            return true;
        }
    }

}
