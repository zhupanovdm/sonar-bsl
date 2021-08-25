package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.zhupanovdm.bsl.grammar.BslGrammar.*;

public class ModuleMetrics {

    private final int numberOfStatements;
    private final int numberOfFunctions;
    private final String executableLines;

    public ModuleMetrics(AstNode rootTree) {
        Objects.requireNonNull(rootTree, "Cannot compute metrics without a root tree");
        List<AstNode> statements = rootTree.getDescendants(
                ASSIGNMENT_STATEMENT,
                CALL_STATEMENT,
                RETURN_STATEMENT,
                IF_STATEMENT,
                WHILE_STATEMENT,
                FOREACH_STATEMENT,
                FOR_STATEMENT,
                BREAK_STATEMENT,
                CONTINUE_STATEMENT,
                TRY_STATEMENT,
                RAISE_STATEMENT,
                EXECUTE_STATEMENT,
                ADD_HANDLER_STATEMENT,
                REMOVE_HANDLER_STATEMENT,
                GOTO_STATEMENT,
                EMPTY_STATEMENT);

        Set<Integer> alreadyMarked = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (AstNode descendant : statements) {
            int line = descendant.getTokenLine();
            if (alreadyMarked.add(line))
                sb.append(line).append("=1;");
        }

        executableLines = sb.toString();
        numberOfStatements = statements.size();
        numberOfFunctions = rootTree.getDescendants(FUNC_DEF, PROC_DEF).size();
    }

    public int getNumberOfFunctions() {
        return numberOfFunctions;
    }

    public int getNumberOfStatements() {
        return numberOfStatements;
    }

    public String getExecutableLines() {
        return executableLines;
    }

}
