package org.zhupanovdm.bsl.tree.module;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.Expression;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreprocessorElsif extends BslTree {
    private Expression condition;

    public PreprocessorElsif(PreprocessorIf parent, Token token) {
        super(parent, token);
        parent.getElsIfBranches().add(this);
    }

    @Override
    public String toString() {
        return "#ElsIf " + condition + " Then {" + getBody().size() + '}';
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPreprocessorElsif(this);
    }
}
