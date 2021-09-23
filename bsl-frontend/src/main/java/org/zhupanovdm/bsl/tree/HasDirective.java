package org.zhupanovdm.bsl.tree;

import org.zhupanovdm.bsl.tree.definition.Directive;

public interface HasDirective {
    Directive getDirective();
    void setDirective(Directive directive);
}
