package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.HasCondition;

import static org.zhupanovdm.bsl.tree.BslTree.Type.WHILE_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class WhileStatement extends BslTree implements HasCondition {
    private BslTree condition;

    public WhileStatement() {
        super(null, WHILE_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitWhileStatement(this);
    }

    @Override
    public String toString() {
        return "While " + condition + " Do {" + getBody().size() + "}";
    }
}
