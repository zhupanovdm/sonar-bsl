package org.zhupanovdm.bsl.symbol;

import lombok.Data;
import lombok.ToString;
import org.zhupanovdm.bsl.tree.BslTree;

import java.util.LinkedList;
import java.util.List;

@Data
@ToString(exclude = {"parent", "scopes", "symbols", "refs"})
public class Scope {
    private final Scope parent;
    private final BslTree owner;
    private final List<Scope> scopes = new LinkedList<>();
    private final List<Symbol> symbols = new LinkedList<>();
    private final List<Symbol> refs = new LinkedList<>();

    public Scope(Scope parent, BslTree owner) {
        this.parent = parent;
        this.owner = owner;
    }
}