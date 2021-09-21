package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.zhupanovdm.bsl.api.BslGrammar.*;

public class ModuleMetrics {

    private final int numberOfStatements;
    private final int numberOfFunctions;
    private final String executableLines;

    public ModuleMetrics(AstNode rootTree) {
        Objects.requireNonNull(rootTree, "Cannot compute metrics without a root tree");
        List<AstNode> statements = rootTree.getDescendants(
                ASSIGN_STMT,
                CALL_STMT,
                RETURN_STMT,
                IF_STMT,
                WHILE_STMT,
                FOREACH_STMT,
                FOR_STMT,
                BREAK_STMT,
                CONTINUE_STMT,
                TRY_STMT,
                RAISE_STMT,
                EXECUTE_STMT,
                ADD_HANDLER_STMT,
                REMOVE_HANDLER_STMT,
                GOTO_STMT,
                EMPTY_STMT);

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
