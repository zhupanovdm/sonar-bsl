package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import org.zhupanovdm.bsl.BslAstVisitor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static org.zhupanovdm.bsl.BslGrammar.*;

public class ComplexityVisitor extends BslAstVisitor {

    private int complexity;

    public int getComplexity() {
        return complexity;
    }

    @Override
    public List<AstNodeType> subscribedTo() {
        return Arrays.asList(
                FUNC_DEF,
                PROC_DEF,

                IF_STATEMENT,
                WHILE_STATEMENT,
                FOR_STATEMENT,
                FOREACH_STATEMENT,
                TRY_STATEMENT,

                OR_EXPRESSION,
                AND_EXPRESSION,
                NOT_EXPRESSION
        );
    }

    @Override
    public void visitFile(@Nullable AstNode node) {
        complexity = 0;
    }

    @Override
    public void visitNode(AstNode node) {
        complexity++;
    }
}