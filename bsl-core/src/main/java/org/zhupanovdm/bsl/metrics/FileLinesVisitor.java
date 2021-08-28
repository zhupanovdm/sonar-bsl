package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import org.zhupanovdm.bsl.BslAstVisitor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sonar.sslr.api.GenericTokenType.EOF;
import static org.zhupanovdm.bsl.utils.BslCommentAnalyser.*;

public class FileLinesVisitor extends BslAstVisitor {
    private final Set<Integer> linesOfCode = new HashSet<>();
    private final Set<Integer> linesOfComments = new HashSet<>();
    private final Set<Integer> linesNoSonar = new HashSet<>();

    public Set<Integer> getLinesOfCode() {
        return Collections.unmodifiableSet(linesOfCode);
    }

    public Set<Integer> getLinesOfComments() {
        return Collections.unmodifiableSet(linesOfComments);
    }

    public Set<Integer> getLinesNoSonar() {
        return Collections.unmodifiableSet(linesNoSonar);
    }

    @Override
    public List<AstNodeType> subscribedTo() {
        return Collections.emptyList();
    }

    @Override
    public void visitFile(@Nullable AstNode node) {
        linesOfCode.clear();
        linesOfComments.clear();
        linesNoSonar.clear();
    }

    @Override
    public void visitToken(Token token) {
        if (!token.getType().equals(EOF)) {
            linesOfCode.add(token.getLine());
        }

        for (Trivia trivia : token.getTrivia()) {
            if (trivia.isComment()) {
                String content = getContents(trivia.getToken().getOriginalValue());
                int line = trivia.getToken().getLine();
                if (isNoSonar(content)) {
                    linesOfComments.remove(line);
                    linesNoSonar.add(line);
                } else if (!isBlank(content) && !linesNoSonar.contains(line)) {
                    linesOfComments.add(line);
                }
            }
        }
    }

}
