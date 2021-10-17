package org.zhupanovdm.bsl.checks;

import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.BslTrivia;

class VerifierCommentsSubscriber implements BslTreeSubscriber {
    private final VerifyingSampleModuleContext context;

    public VerifierCommentsSubscriber(VerifyingSampleModuleContext context) {
        this.context = context;
    }

    @Override
    public void onEnterNode(BslTree node) {
        for (BslToken token : node.getTokens()) {
            for (BslTrivia comment : token.getComments()) {
                BslToken t = comment.getTokens().get(0);
                context.getVerifier().addComment(
                        context.getFile().getPath(),
                        t.getPosition().getLine(),
                        t.getPosition().getColumn(),
                        t.getValue(),
                        2,
                        0);
            }
        }
    }
}
