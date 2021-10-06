package org.zhupanovdm.bsl.symbol;

import lombok.Data;
import org.zhupanovdm.bsl.tree.BslTree;

import java.util.LinkedList;
import java.util.List;

@Data
public class Symbol {
    private String name;
    private Scope scope;
    private BslTree owner;
    private final List<InferredType> types = new LinkedList<>();
    private final List<SymbolReference> references = new LinkedList<>();

    public Symbol(Scope scope, BslTree owner, String name) {
        this.scope = scope;
        this.owner = owner;
        this.name = name;
    }

    public int getPosition() {
        return owner.getFirstToken().getLine();
    }

    public boolean isVisibleFor(SymbolReference reference) {
        return name.equals(reference.getName()) && reference.getPosition() >= getPosition();
    }

    public boolean isBuiltIn() {
        return false;
    }
}
