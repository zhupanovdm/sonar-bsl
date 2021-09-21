package org.zhupanovdm.bsl.tree.expression;


import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndexPostfix extends Postfix {
    private Expression index;

    public IndexPostfix(BslTree parent, Token token) {
        super(parent, token);
    }

    @Override
    public String toString() {
        return "[" + index + ']' + (getPostfix() == null ? "" : getPostfix());
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitIndexPostfix(this);
    }
}
