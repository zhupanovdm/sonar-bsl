package org.zhupanovdm.bsl.tree.module;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;
import org.zhupanovdm.bsl.tree.expression.Expression;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreprocessorIf extends BslTree {
    private Expression condition;
    private final List<PreprocessorElsif> elsIfBranches = new LinkedList<>();

    public PreprocessorIf(BslTree parent, Token token) {
        super(parent, token);
        parent.getBody().add(this);
    }

    @Override
    public String toString() {
        return "#If " + condition + " Then {" + getBody().size() + '}';
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitPreprocessorIf(this);
    }
}