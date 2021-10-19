package org.zhupanovdm.bsl.sonar;

import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonarsource.analyzer.commons.TokenLocation;
import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.Position;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.util.Collection;

import static org.zhupanovdm.bsl.tree.BslToken.Type.*;

public class BslModuleHighlighter implements BslTreeSubscriber {
    private final BslModuleContext context;
    private NewHighlighting highlighting;

    public BslModuleHighlighter(BslModuleContext context) {
        this.context = context;
    }

    @Override
    public void onEnterFile(AbstractModuleContext fileContext) {
        highlighting = context.getSensor().newHighlighting().onFile(context.getFile().getInput());
    }

    @Override
    public void onLeaveFile(AbstractModuleContext context) {
        highlighting.save();
    }

    @Override
    public void onEnterNode(BslTree node) {
        node.getTokens().forEach(this::visitToken);
    }

    private void visitToken(BslToken token) {
        if (token.is(CONSTANT)) {
            highlight(token, TypeOfText.CONSTANT);
        } else if (token.is(KEYWORD)) {
            highlight(token, TypeOfText.KEYWORD);
        } else if (token.is(KEYWORD_SYNTACTIC)) {
            highlight(token, TypeOfText.KEYWORD_LIGHT);
        } else if (token.is(STRING_LITERAL)) {
            highlight(token, TypeOfText.STRING);
        } else if (token.is(PREPROCESSOR)) {
            highlight(token, TypeOfText.PREPROCESS_DIRECTIVE);
        }
        token.getComments().forEach(comment -> highlightComment(comment.getTokens()));
    }

    private void highlight(BslToken token, TypeOfText typeOfText) {
        TokenLocation location = location(token);
        highlighting.highlight(location.startLine(), location.startLineOffset(), location.endLine(), location.endLineOffset(), typeOfText);
    }

    private void highlightComment(Collection<BslToken> tokens) {
        tokens.forEach(token -> highlight(token, TypeOfText.COMMENT));
    }

    private static TokenLocation location(BslToken token) {
        Position position = token.getPosition();
        return new TokenLocation(position.getLine(), position.getColumn(), token.getValue());
    }
}
