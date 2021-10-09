package org.zhupanovdm.bsl.tree;

import org.zhupanovdm.bsl.tree.definition.Directive;

import java.util.Optional;

public interface HasDirective {
    Optional<Directive> getDirective();
    void setDirective(Directive directive);
}
