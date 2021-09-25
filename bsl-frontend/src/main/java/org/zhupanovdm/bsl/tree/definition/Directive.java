package org.zhupanovdm.bsl.tree.definition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.grammar.BslDirective;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import static org.zhupanovdm.bsl.tree.BslTree.Type.DIRECTIVE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Directive extends BslTree {
    private BslDirective value;

    public Directive(BslTree parent) {
        super(parent, DIRECTIVE);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitCompilationDirective(this);
    }

    @Override
    public String toString() {
        return "&" + value;
    }
}
