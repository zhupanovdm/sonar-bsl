package org.zhupanovdm.bsl.metrics;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.BslTrivia;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.statement.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModuleMetrics implements BslTreeSubscriber {
    private static final String NO_SONAR = "NOSONAR";

    private final Set<Integer> linesOfCode = new HashSet<>();
    private final Set<Integer> linesOfComments = new HashSet<>();
    private final Set<Integer> linesNoSonar = new HashSet<>();
    private final Set<Integer> executableLines = new HashSet<>();

    @Getter
    private int numberOfStatements;

    @Getter
    private int numberOfFunctions;

    public Set<Integer> getExecutableLines() {
        return Collections.unmodifiableSet(executableLines);
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

    @Override
    public void init() {
        linesOfCode.clear();
        linesOfComments.clear();
        linesNoSonar.clear();
        executableLines.clear();

        numberOfStatements = 0;
        numberOfFunctions = 0;
    }

    @Override
    public void onEnterNode(BslTree node) {
        node.getTokens().forEach(this::visitToken);
    }

    @Override
    public void onVisitCallableDefinition(CallableDefinition def) {
        numberOfFunctions++;
    }

    @Override
    public void onVisitAssignmentStatement(AssignmentStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitCallStatement(CallStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitIfStatement(IfStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitWhileStatement(WhileStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitForStatement(ForStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitForEachStatement(ForEachStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitReturnStatement(ReturnStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitContinueStatement(ContinueStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitBreakStatement(BreakStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitRaiseStatement(RaiseStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitEmptyStatement(EmptyStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitTryStatement(TryStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitExecuteStatement(ExecuteStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitGotoStatement(GotoStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitAddHandlerStatement(AddHandlerStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    @Override
    public void onVisitRemoveHandlerStatement(RemoveHandlerStatement stmt) {
        numberOfStatements++;
        addExecutableLine(stmt);
    }

    private void addExecutableLine(BslTree node) {
        BslToken token = node.getFirstToken();
        if (token != null) {
            executableLines.add(token.getPosition().getLine());
        }
    }

    private void visitToken(BslToken token) {
        if (!token.is(BslToken.Type.EOF)) {
            linesOfCode.add(token.getPosition().getLine());
        }

        for (BslTrivia trivia : token.getComments()) {
            String content = getContents(trivia.getValue());
            int line = trivia.getTokens().get(0).getPosition().getLine();
            if (isNoSonar(content)) {
                linesOfComments.remove(line);
                linesNoSonar.add(line);
            } else if (!isBlank(content) && !linesNoSonar.contains(line)) {
                linesOfComments.add(line);
            }
        }
    }

    private static boolean isBlank(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetterOrDigit(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String getContents(String comment) {
        return comment.substring(2);
    }

    private static boolean isNoSonar(String content) {
        return content.contains(NO_SONAR);
    }
}
