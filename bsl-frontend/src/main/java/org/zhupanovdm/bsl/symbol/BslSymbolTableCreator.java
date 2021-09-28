package org.zhupanovdm.bsl.symbol;

import lombok.Getter;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.definition.Parameter;
import org.zhupanovdm.bsl.tree.definition.Variable;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;
import org.zhupanovdm.bsl.tree.module.Module;

import java.util.Deque;
import java.util.LinkedList;

import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class BslSymbolTableCreator implements BslTreeSubscriber {
    @Getter
    private BslSymbolTable table;
    private final Deque<Scope> scopes = new LinkedList<>();

    @Override
    public void init() {
        table = new BslSymbolTable();
        scopes.clear();
    }

    @Override
    public void onVisitModule(Module module) {
        createGlobalScope(module);
    }

    @Override
    public void onVisitVariable(Variable variable) {
        createSymbol(variable, variable.getName());
    }

    @Override
    public void onVisitCallableDefinition(CallableDefinition def) {
        createSymbol(def, def.getName());
        createScope(def);
    }

    @Override
    public void onVisitParameter(Parameter parameter) {
        createSymbol(parameter, parameter.getName());
    }

    @Override
    public void onVisitReferenceExpression(ReferenceExpression expr) {
        createReference(expr, expr.getName());
    }

    @Override
    public void onLeaveNode(BslTree node) {
        if (node.is(MODULE, FUNCTION, PROCEDURE)) {
            scopes.pop();
        }
    }

    private void createGlobalScope(BslTree owner) {
        Scope scope = new Scope(null, owner);
        scopes.push(scope);
        table.setGlobalScope(scope);
    }

    private void createScope(BslTree owner) {
        Scope parent = scopes.getFirst();
        Scope scope = new Scope(parent, owner);
        parent.getScopes().add(scope);
        scopes.push(scope);
    }

    private void createSymbol(BslTree owner, String name) {
        if (scopes.isEmpty()) {
            createGlobalScope(null);
        }
        Scope scope = scopes.getFirst();
        scope.getSymbols().add(new Symbol(scope, owner, name));
    }

    private void createReference(BslTree owner, String name) {
        if (scopes.isEmpty()) {
            createGlobalScope(null);
        }
        Scope scope = scopes.getFirst();
        scope.getRefs().add(new Symbol(scope, owner, name));
    }
}