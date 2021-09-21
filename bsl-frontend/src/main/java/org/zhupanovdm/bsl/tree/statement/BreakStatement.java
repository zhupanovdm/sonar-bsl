package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class BreakStatement extends Statement {
    public BreakStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Break";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitBreakStatement(this);
    }
}
