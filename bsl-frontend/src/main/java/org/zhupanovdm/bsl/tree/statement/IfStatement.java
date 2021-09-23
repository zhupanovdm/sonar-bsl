package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.HasCondition;

import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class IfStatement extends BslTree implements HasCondition {
    private BslTree condition;
    private final List<ElsIfBranch> elsIfBranches = new LinkedList<>();
    private ElseClause elseClause;

    public IfStatement() {
        super(null, IF_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitIfStatement(this);
    }

    @Override
    public String toString() {
        return "If " + condition + " Then {" + getBody().size() + "}";
    }
}
