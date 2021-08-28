package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BslAstVisitor {

    private AstNode rootTree;

    private Set<AstNodeType> subscribedKinds = null;

    public abstract List<AstNodeType> subscribedTo();

    private Set<AstNodeType> subscribedKinds() {
        if (subscribedKinds == null) {
            subscribedKinds = Collections.unmodifiableSet(new HashSet<>(subscribedTo()));
        }
        return subscribedKinds;
    }

    public void visitFile(@Nullable AstNode node) {
    }

    public void leaveFile(@Nullable AstNode node) {
    }

    public void visitNode(AstNode node) {
    }

    public void leaveNode(AstNode node) {
    }

    public void visitToken(Token token) {
    }

    public AstNode getRootTree() {
        return rootTree;
    }

    public void scanFile(AstNode node) {
        rootTree = node;
        visitFile(node);
        if (node != null) {
            scanNode(node);
        }
        leaveFile(node);
    }

    public void scanNode(AstNode node) {
        boolean isSubscribedType = subscribedKinds().contains(node.getType());

        if (isSubscribedType) {
            visitNode(node);
        }

        List<AstNode> children = node.getChildren();
        if (children.isEmpty()) {
            node.getTokens().forEach(this::visitToken);
        } else {
            children.forEach(this::scanNode);
        }

        if (isSubscribedType) {
            leaveNode(node);
        }
    }

}
