package org.zhupanovdm.bsl.symbol;

import lombok.Data;
import org.zhupanovdm.bsl.tree.BslTree;

@Data
public class Symbol {
    private String name;
    private Scope scope;
    private BslTree owner;

    public Symbol(Scope scope, BslTree owner, String name) {
        this.scope = scope;
        this.owner = owner;
        this.name = name;
    }
}
