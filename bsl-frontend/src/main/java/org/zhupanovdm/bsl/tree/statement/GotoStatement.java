package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class GotoStatement extends Statement {
    private Label label;

    public GotoStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Goto " + label;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitGotoStatement(this);
    }
}
