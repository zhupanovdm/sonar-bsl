package org.zhupanovdm.bsl.tree.statement;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class TryStatement extends Statement {
    private ExceptClause exceptClause;

    public TryStatement(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Try {" + getBody().size() + "}";
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitTryStatement(this);
    }
}
