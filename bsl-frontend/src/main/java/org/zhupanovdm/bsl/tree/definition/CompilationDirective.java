package org.zhupanovdm.bsl.tree.definition;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.grammar.BslDirective;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeVisitor;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompilationDirective extends BslTree {
    private BslDirective value;

    public CompilationDirective(BslTree parent, Token token, BslDirective value) {
        super(parent, token);
        this.value = value;
    }

    @Override
    public String toString() {
        return "&" + value;
    }

    @Override
    public void accept(BslTreeVisitor visitor) {
        visitor.visitCompilationDirective(this);
    }
}
