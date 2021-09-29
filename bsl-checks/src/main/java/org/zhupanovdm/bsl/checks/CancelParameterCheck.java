package org.zhupanovdm.bsl.checks;

import org.sonar.check.Rule;
import org.zhupanovdm.bsl.BslCheck;
import org.zhupanovdm.bsl.Issue;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.Named;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;
import org.zhupanovdm.bsl.tree.definition.Parameter;
import org.zhupanovdm.bsl.tree.definition.Variable;
import org.zhupanovdm.bsl.tree.statement.AssignmentStatement;

import java.util.regex.Pattern;

import static org.zhupanovdm.bsl.tree.BslTree.Type.*;
import static org.zhupanovdm.bsl.utils.StringUtils.caseInsensitiveBilingualRegexpString;

@Rule(key = "CancelParameter")
public class CancelParameterCheck extends BslCheck {
    private static final String MESSAGE_LAST = "Параметр 'Отмена' должен быть последним параметром процедуры\\функции.";
    private static final String MESSAGE_TRUE = "Параметру 'Отмена' должно присваиваться только значение Истина.";

    private static final Pattern CANCEL_PATTERN = Pattern.compile(caseInsensitiveBilingualRegexpString("Cancel", "Отмена"));

    private boolean hasCancelParam;

    @Override
    public void init() {
        hasCancelParam = false;
    }

    @Override
    public void onVisitCallableDefinition(CallableDefinition def) {
        hasCancelParam = false;
    }

    @Override
    public void onLeaveNode(BslTree node) {
        if (node.is(FUNCTION, PROCEDURE)) {
            hasCancelParam = false;
        }
    }

    @Override
    public void onVisitParameter(Parameter parameter) {
        if (isCancel(parameter)) {
            hasCancelParam = true;
            int desiredIndex = parameter.getParent().as(CallableDefinition.class).getParameters().size() - 1;
            if (parameter.getIndex() != desiredIndex) {
                saveIssue(Issue.lineIssue(parameter.getFirstToken().getLine(), MESSAGE_LAST));
            }
        }
    }

    @Override
    public void onVisitAssignmentStatement(AssignmentStatement stmt) {
        if (hasCancelParam &&
                stmt.getTarget().is(VARIABLE) &&
                isCancel(stmt.getTarget().as(Variable.class)) &&
                !stmt.getExpression().is(TRUE)
        ) {
            saveIssue(Issue.lineIssue(stmt.getFirstToken().getLine(), MESSAGE_TRUE));
        }
    }

    private static boolean isCancel(Named node) {
        return CANCEL_PATTERN.matcher(node.getName()).matches();
    }
}