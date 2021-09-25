package org.zhupanovdm.bsl.metrics;

import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.BslTrivia;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.sonar.sslr.api.GenericTokenType.EOF;

public class FileLinesVisitor implements BslTreeSubscriber {
    private static final String NOSONAR = "NOSONAR";

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
    public void onEnterNode(BslTree node) {
        for (BslToken token : node.getTokens()) {
            visitToken(token);
        }
    }

    private void visitToken(BslToken token) {
        if (!token.getType().equals(EOF)) {
            linesOfCode.add(token.getLine());
        }

        for (BslTrivia trivia : token.getComments()) {
            String content = getContents(trivia.getValue());
            int line = trivia.getTokens().get(0).getLine();
            if (isNoSonar(content)) {
                linesOfComments.remove(line);
                linesNoSonar.add(line);
            } else if (!isBlank(content) && !linesNoSonar.contains(line)) {
                linesOfComments.add(line);
            }
        }
    }

    public static boolean isBlank(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetterOrDigit(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getContents(String comment) {
        return comment.substring(2);
    }

    public static boolean isNoSonar(String content) {
        return content.contains(NOSONAR);
    }
}