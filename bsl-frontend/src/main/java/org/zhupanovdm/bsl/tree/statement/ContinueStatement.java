package org.zhupanovdm.bsl.tree.statement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

import static org.zhupanovdm.bsl.tree.BslTree.Type.CONTINUE_STMT;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContinueStatement extends BslTree {
    public ContinueStatement() {
        super(null, CONTINUE_STMT);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitContinueStatement(this);
    }

    @Override
    public String toString() {
        return "Continue";
    }
}
