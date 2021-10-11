package org.zhupanovdm.bsl.tree;

import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.tree.definition.*;
import org.zhupanovdm.bsl.tree.expression.*;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;
import org.zhupanovdm.bsl.tree.statement.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BslTreePublisher {
    protected final List<BslTreeSubscriber> subscribers = new LinkedList<>();
    private final TreeSubscriber subscriber = new TreeSubscriber();

    public void subscribe(BslTreeSubscriber ...subscribers) {
        subscribe(Arrays.asList(subscribers));
    }

    public void subscribe(Collection<? extends BslTreeSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    public void scan(AbstractModuleContext context) {
        subscriber.init();
        subscriber.onEnterFile(context);
        publish(context.getEntry());
        subscriber.onLeaveFile(context);
    }

    public void scan(BslTree tree) {
        subscriber.init();
        publish(tree);
    }

    private void publish(BslTree node) {
        if (node != null) {
            node.accept(subscriber);
        }
    }

    private void publish(Collection<? extends BslTree> nodes) {
        nodes.forEach(this::publish);
    }

    public static void publish(BslTree tree, BslTreeSubscriber ...subscribers) {
        BslTreePublisher publisher = new BslTreePublisher();
        publisher.subscribe(subscribers);
        publisher.scan(tree);
    }

    private class TreeSubscriber implements BslTreeSubscriber {
        @Override
        public void init() {
            subscribers.forEach(BslTreeSubscriber::init);
        }

        @Override
        public void onEnterFile(AbstractModuleContext context) {
            subscribers.forEach(subscriber -> subscriber.onEnterFile(context));
        }

        @Override
        public void onLeaveFile(AbstractModuleContext context) {
            subscribers.forEach(subscriber -> subscriber.onLeaveFile(context));
        }

        @Override
        public void onEnterNode(BslTree node) {
            subscribers.forEach(subscriber -> {
                subscriber.onEnterNode(node);
                node.accept(subscriber);
            });
        }

        @Override
        public void onLeaveNode(BslTree node) {
            subscribers.forEach(subscriber -> subscriber.onLeaveNode(node));
        }

        @Override
        public void onVisitModule(ModuleRoot module) {
            onEnterNode(module);
            publish(module.getBody());
            onLeaveNode(module);
        }

        @Override
        public void onVisitPreprocessorIf(PreprocessorIf pp) {
            onEnterNode(pp);
            publish(pp.getCondition());
            publish(pp.getBody());
            publish(pp.getElsIfBranches());
            onLeaveNode(pp);
        }

        @Override
        public void onVisitPreprocessorElsif(PreprocessorElsif pp) {
            onEnterNode(pp);
            publish(pp.getCondition());
            publish(pp.getBody());
            onLeaveNode(pp);
        }

        @Override
        public void onVisitCallableDefinition(CallableDefinition def) {
            onEnterNode(def);
            def.getDirective().ifPresent(BslTreePublisher.this::publish);
            publish(def.getParameters());
            publish(def.getBody());
            onLeaveNode(def);
        }

        @Override
        public void onVisitVariablesDefinition(VariablesDefinition def) {
            onEnterNode(def);
            def.getDirective().ifPresent(BslTreePublisher.this::publish);
            publish(def.getBody());
            onLeaveNode(def);
        }

        @Override
        public void onVisitAssignmentStatement(AssignmentStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getTarget());
            publish(stmt.getExpression());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitCallStatement(CallStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getExpression());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitIfStatement(IfStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getCondition());
            publish(stmt.getBody());
            publish(stmt.getElsIfBranches());
            stmt.getElseClause().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitElsIfBranch(ElsIfBranch branch) {
            onEnterNode(branch);
            publish(branch.getCondition());
            publish(branch.getBody());
            onLeaveNode(branch);
        }

        @Override
        public void onVisitElseClause(ElseClause clause) {
            onEnterNode(clause);
            publish(clause.getBody());
            onLeaveNode(clause);
        }

        @Override
        public void onVisitWhileStatement(WhileStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getCondition());
            publish(stmt.getBody());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitForStatement(ForStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getVariable());
            publish(stmt.getInit());
            publish(stmt.getCondition());
            publish(stmt.getBody());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitForEachStatement(ForEachStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getVariable());
            publish(stmt.getCollection());
            publish(stmt.getBody());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitReturnStatement(ReturnStatement stmt) {
            onEnterNode(stmt);
            stmt.getExpression().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitContinueStatement(ContinueStatement stmt) {
            onEnterNode(stmt);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitBreakStatement(BreakStatement stmt) {
            onEnterNode(stmt);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitRaiseStatement(RaiseStatement stmt) {
            onEnterNode(stmt);
            stmt.getExpression().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitEmptyStatement(EmptyStatement stmt) {
            onEnterNode(stmt);
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitTryStatement(TryStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getBody());
            publish(stmt.getExceptClause());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitExceptClause(ExceptClause clause) {
            onEnterNode(clause);
            publish(clause.getBody());
            onLeaveNode(clause);
        }

        @Override
        public void onVisitExecuteStatement(ExecuteStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getExpression());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitLabelDefinition(LabelDefinition def) {
            onEnterNode(def);
            publish(def.getLabel());
            onLeaveNode(def);
        }

        @Override
        public void onVisitGotoStatement(GotoStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getLabel());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitAddHandlerStatement(AddHandlerStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getEvent());
            publish(stmt.getHandler());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitRemoveHandlerStatement(RemoveHandlerStatement stmt) {
            onEnterNode(stmt);
            publish(stmt.getEvent());
            publish(stmt.getHandler());
            onLeaveNode(stmt);
        }

        @Override
        public void onVisitVariable(Variable variable) {
            onEnterNode(variable);
            onLeaveNode(variable);
        }

        @Override
        public void onVisitParameter(Parameter parameter) {
            onEnterNode(parameter);
            parameter.getDefaultValue().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(parameter);
        }

        @Override
        public void onVisitLabel(Label label) {
            onEnterNode(label);
            onLeaveNode(label);
        }

        @Override
        public void onVisitDirective(Directive directive) {
            onEnterNode(directive);
            onLeaveNode(directive);
        }

        @Override
        public void onVisitBinaryExpression(BinaryExpression expr) {
            onEnterNode(expr);
            publish(expr.getLeft());
            publish(expr.getRight());
            onLeaveNode(expr);
        }

        @Override
        public void onVisitUnaryExpression(UnaryExpression expr) {
            onEnterNode(expr);
            publish(expr.getExpression());
            onLeaveNode(expr);
        }

        @Override
        public void onVisitNewExpression(NewExpression expr) {
            onEnterNode(expr);
            expr.getPostfix().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(expr);
        }

        @Override
        public void onVisitPostfixExpression(PostfixExpression expr) {
            onEnterNode(expr);
            publish(expr.getReference());
            expr.getPostfix().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(expr);
        }

        @Override
        public void onVisitCallPostfix(CallPostfix postfix) {
            onEnterNode(postfix);
            publish(postfix.getArguments());
            postfix.getPostfix().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(postfix);
        }

        @Override
        public void onVisitDereferencePostfix(DereferencePostfix postfix) {
            onEnterNode(postfix);
            postfix.getPostfix().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(postfix);
        }

        @Override
        public void onVisitIndexPostfix(IndexPostfix postfix) {
            onEnterNode(postfix);
            publish(postfix.getIndex());
            postfix.getPostfix().ifPresent(BslTreePublisher.this::publish);
            onLeaveNode(postfix);
        }

        @Override
        public void onVisitReferenceExpression(ReferenceExpression expr) {
            onEnterNode(expr);
            onLeaveNode(expr);
        }

        @Override
        public void onVisitParenthesisExpression(ParenthesisExpression expr) {
            onEnterNode(expr);
            publish(expr.getExpression());
            onLeaveNode(expr);
        }

        @Override
        public void onVisitTernaryExpression(TernaryExpression expr) {
            onEnterNode(expr);
            publish(expr.getCondition());
            publish(expr.getTrueExpression());
            publish(expr.getFalseExpression());
            onLeaveNode(expr);
        }

        @Override
        public void onVisitEmptyExpression(EmptyExpression expr) {
            onEnterNode(expr);
            onLeaveNode(expr);
        }

        @Override
        public void onVisitPrimitiveExpression(PrimitiveExpression expr) {
            onEnterNode(expr);
            publish(expr.getBody());
            onLeaveNode(expr);
        }
    }
}
