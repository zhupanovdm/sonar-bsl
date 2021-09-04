package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.AstVisitor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CognitiveComplexityVisitorStub implements AstVisitor {

    private int complexity;
    private int nesting;

    @Override
    public List<AstNodeType> getAstNodeTypesToVisit() {
        return Collections.emptyList();
    }

    @Override
    public void visitFile(@Nullable AstNode ast) {
        complexity = 0;
        nesting = 0;
    }

    @Override
    public void leaveFile(@Nullable AstNode ast) {
    }

    @Override
    public void visitNode(AstNode ast) {
        nesting++;
    }

    @Override
    public void leaveNode(AstNode ast) {
        nesting--;
    }
}
