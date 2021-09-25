package org.zhupanovdm.bsl.metrics;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.statement.*;

import java.util.HashSet;
import java.util.Set;

public class ModuleMetrics implements BslTreeSubscriber {
    @Getter
    private int numberOfStatements;

    @Getter
    private int numberOfFunctions;

    private final Set<Integer> alreadyMarked = new HashSet<>();
    private final StringBuilder executableLinesBuilder = new StringBuilder();

    public String getExecutableLines() {
        return executableLinesBuilder.toString();
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
        if (token == null) {
            return;
        }
        int line = token.getLine();
        if (alreadyMarked.add(line))
            executableLinesBuilder.append(line).append("=1;");
    }
}
