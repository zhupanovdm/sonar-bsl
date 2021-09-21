package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContinueStatement extends Statement {
    public ContinueStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Continue";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitContinueStatement(this);
    }
}
