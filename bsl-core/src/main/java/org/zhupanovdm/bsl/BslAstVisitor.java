package org.zhupanovdm.bsl;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class BslAstVisitor {

    private Set<AstNodeType> subscribedKinds = null;

    public abstract List<AstNodeType> subscribedTo();

    private Set<AstNodeType> subscribedKinds() {
        /*
        if (subscribedKinds == null)
            subscribedKinds = ImmutableSet.copyOf(subscribedTo());
        return subscribedKinds;
         */

        return Collections.emptySet();
    }

    public void visitNode(AstNode node) {
    }

    public void leaveNode(AstNode node) {
    }

    public void visitToken(Token token) {
    }

    public void scan(AstNode node) {
        boolean isSubscribedType = subscribedKinds().contains(node.getType());

        if (isSubscribedType)
            visitNode(node);

        List<AstNode> children = node.getChildren();
        if (children.isEmpty()) {
            node.getTokens().forEach(this::visitToken);
        } else {
            children.forEach(this::scan);
        }

        if (isSubscribedType)
            leaveNode(node);
    }

}
