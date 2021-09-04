package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.AstVisitor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static org.zhupanovdm.bsl.BslGrammar.*;
import static org.zhupanovdm.bsl.api.BslKeyword.ELSE;
import static org.zhupanovdm.bsl.api.BslKeyword.ELSIF;

public class CyclomaticComplexityVisitor implements AstVisitor {

    private int complexity;

    public int getComplexity() {
        return complexity;
    }

    @Override
    public List<AstNodeType> getAstNodeTypesToVisit() {
        return Arrays.asList(
                FUNC_DEF,
                PROC_DEF,

                IF_STATEMENT, ELSE, ELSIF,
                WHILE_STATEMENT,
                FOR_STATEMENT,
                FOREACH_STATEMENT,

                OR_EXPRESSION,
                AND_EXPRESSION,
                NOT_EXPRESSION,

                TERNARY_EXPRESSION
        );
    }

    @Override
    public void visitFile(@Nullable AstNode ast) {
        complexity = 0;
    }

    @Override
    public void leaveFile(@Nullable AstNode ast) {
    }

    @Override
    public void visitNode(AstNode ast) {
        complexity++;
    }

    @Override
    public void leaveNode(AstNode ast) {
    }

}