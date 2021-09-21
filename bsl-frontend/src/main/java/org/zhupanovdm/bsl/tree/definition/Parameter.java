package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.PrimitiveExpression;

@Data
@EqualsAndHashCode(callSuper = true)
public class Parameter extends BslTree {
    private Identifier identifier;
    private Val val;
    private PrimitiveExpression defaultValue;

    public Parameter(CallableDefinition parent, Token token) {
        super(parent, token);
        parent.getParameters().add(this);
    }

    @Override
    public String toString() {
        return (val == null ? "" : val) + " " + identifier + (defaultValue == null ? "" : " = " + defaultValue);
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitParameter(this);
    }

    public static class Val extends BslTree {
        public Val(Parameter parent, Token token) {
            super(parent, token);
        }

        @Override
        public String toString() {
            return "Val";
        }

        @Override
        public void accept(BslTreeVisitor visitor) {
            visitor.visitParameterVal(this);
        }
    }
}
