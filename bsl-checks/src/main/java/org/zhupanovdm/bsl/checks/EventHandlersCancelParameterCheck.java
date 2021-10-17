package org.zhupanovdm.bsl.checks;

import org.sonar.check.Rule;
import org.zhupanovdm.bsl.Check;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.Named;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.definition.Parameter;
import org.zhupanovdm.bsl.tree.definition.Variable;
import org.zhupanovdm.bsl.tree.expression.BinaryExpression;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;
import org.zhupanovdm.bsl.tree.statement.AssignmentStatement;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

import static org.zhupanovdm.bsl.tree.BslTree.Type.*;
import static org.zhupanovdm.bsl.utils.StringUtils.caseInsensitiveBilingualRegexpString;

@Rule(key = "STD68")
public class EventHandlersCancelParameterCheck extends Check {
    private static final String MESSAGE_FALSE = "Не следует присваивать параметру 'Отказ' значние Ложь.";

    private Parameter cancelParam;

    private final Collection<Pattern> handlersPatterns = new LinkedList<>();

    @Override
    public void init() {
        cancelParam = null;

        if (handlersPatterns.isEmpty()) {
            handlersPatterns.add(Pattern.compile(caseInsensitiveBilingualRegexpString("BeforeWrite", "ПередЗаписью")));
            handlersPatterns.add(Pattern.compile(caseInsensitiveBilingualRegexpString("OnWrite", "ПриЗаписи")));
            handlersPatterns.add(Pattern.compile(caseInsensitiveBilingualRegexpString("FillCheckProcessing", "ОбработкаПроверкиЗаполнения")));
        }
    }

    @Override
    public void onVisitCallableDefinition(CallableDefinition def) {
        cancelParam = null;
    }

    @Override
    public void onLeaveNode(BslTree node) {
        if (node.is(FUNCTION, PROCEDURE)) {
            cancelParam = null;
        }
    }

    @Override
    public void onVisitParameter(Parameter parameter) {
        if (parameter.getIndex() == 0 && hasCancelParam(parameter.getParent().as(CallableDefinition.class))) {
            cancelParam = parameter;
        }
    }

    @Override
    public void onVisitAssignmentStatement(AssignmentStatement stmt) {
        if (cancelParam != null &&
                isCancel(stmt.getTarget()) &&
                !(isTrueAssigned(stmt) || isOrExpression(stmt))) {
            addIssue(MESSAGE_FALSE, stmt.getFirstToken().getPosition().getLine());
        }
    }

    private boolean isTrueAssigned(AssignmentStatement stmt) {
        return stmt.getExpression().is(TRUE);
    }

    private boolean isOrExpression(AssignmentStatement stmt) {
        return stmt.getExpression().is(OR) && isCancel(stmt.getExpression().as(BinaryExpression.class).getLeft());
    }

    private boolean isCancel(BslTree node) {
        if (cancelParam == null) {
            return false;
        }

        if (node.is(VARIABLE) && cancelParam.getName().equalsIgnoreCase(node.as(Variable.class).getName())) {
            return true;
        }

        return node.is(REFERENCE) && cancelParam.getName().equalsIgnoreCase(node.as(ReferenceExpression.class).getName());
    }

    private boolean hasCancelParam(Named node) {
        for (Pattern pattern : handlersPatterns) {
            if (pattern.matcher(node.getName()).matches()) {
                return true;
            }
        }
        return false;
    }
}