package org.zhupanovdm.bsl.symbol;

import lombok.Data;
import org.zhupanovdm.bsl.tree.BslTree;

@Data
public class SymbolReference {
    private String name;
    private Scope scope;
    private BslTree owner;

    public SymbolReference(Scope scope, BslTree owner, String name) {
        this.scope = scope;
        this.owner = owner;
        this.name = name;
    }

    public int getPosition() {
        return owner.getFirstToken().getLine();
    }

    public Symbol resolve() {
        return scope.resolve(this);
    }
}
