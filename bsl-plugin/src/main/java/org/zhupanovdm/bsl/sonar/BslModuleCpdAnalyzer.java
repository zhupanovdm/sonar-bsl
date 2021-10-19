package org.zhupanovdm.bsl.sonar;

import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonarsource.analyzer.commons.TokenLocation;
import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.Position;
import org.zhupanovdm.bsl.tree.BslToken;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.expression.PrimitiveExpression;

import static org.zhupanovdm.bsl.tree.BslToken.Type.*;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class BslModuleCpdAnalyzer implements BslTreeSubscriber {
    public static final String NORMALIZED_IDENTIFIER = "IDENTIFIER";
    public static final String NORMALIZED_NUMERIC = "000";
    public static final String NORMALIZED_STRING = "STRING";
    public static final String NORMALIZED_DATE = "'00010101'";

    private final BslModuleContext context;
    private NewCpdTokens cpdTokens;

    public BslModuleCpdAnalyzer(BslModuleContext context) {
        this.context = context;
    }

    @Override
    public void onEnterFile(AbstractModuleContext module) {
        cpdTokens = context.getSensor().newCpdTokens().onFile(context.getFile().getInput());
    }

    @Override
    public void onLeaveFile(AbstractModuleContext context) {
        cpdTokens.save();
    }

    @Override
    public void onEnterNode(BslTree node) {
        if (node.is(STRING)) {
            return;
        }
        node.getTokens().forEach(this::visitToken);
    }

    @Override
    public void onVisitPrimitiveExpression(PrimitiveExpression expr) {
        if (expr.is(STRING)) {
            if (!expr.getBody().isEmpty()) {
                BslTree last = expr.getBody().get(expr.getBody().size() - 1);

                TokenLocation locationFirst = location(expr.getBody().get(0).getTokens().get(0));
                TokenLocation locationLast = location(last.getTokens().get(last.getTokens().size() - 1));
                cpdTokens.addToken(locationFirst.startLine(), locationFirst.startLineOffset(), locationLast.endLine(), locationLast.endLineOffset(), NORMALIZED_STRING);
            }
        } else if (expr.is(UNDEFINED, NULL, TRUE, FALSE)) {
            addToken(expr.getFirstToken(), expr.getType().toString());
        }
    }

    private void visitToken(BslToken token) {
        if (token.is(EOF, CONSTANT)) {
            return;
        }
        if (token.is(IDENTIFIER)) {
            addToken(token, NORMALIZED_IDENTIFIER);
        } else if (token.is(NUMERIC)) {
            addToken(token, NORMALIZED_NUMERIC);
        } else if (token.is(DATE_LITERAL)) {
            addToken(token, NORMALIZED_DATE);
        } else {
            addToken(token);
        }
    }

    private void addToken(BslToken token) {
        addToken(token, token.getValue().toUpperCase());
    }

    private void addToken(BslToken token, String image) {
        TokenLocation location = location(token);
        cpdTokens.addToken(location.startLine(), location.startLineOffset(), location.endLine(), location.endLineOffset(), image);
    }

    private static TokenLocation location(BslToken token) {
        Position position = token.getPosition();
        return new TokenLocation(position.getLine(), position.getColumn(), token.getValue());
    }
}
