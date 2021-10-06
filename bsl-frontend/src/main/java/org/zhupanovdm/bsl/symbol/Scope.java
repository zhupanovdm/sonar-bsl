package org.zhupanovdm.bsl.symbol;

import lombok.Data;
import lombok.ToString;
import org.zhupanovdm.bsl.tree.BslTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@Data
@ToString(exclude = {"parent", "scopes", "symbols", "references"})
public class Scope {
    private final Scope parent;
    private final BslTree owner;
    private final List<Scope> scopes = new LinkedList<>();
    private final List<Symbol> symbols = new LinkedList<>();
    private final List<SymbolReference> references = new LinkedList<>();

    public Scope(Scope parent, BslTree owner) {
        this.parent = parent;
        this.owner = owner;
    }

    public Symbol resolve(SymbolReference reference) {
        return resolve(reference, resolver(parent, reference));
    }

    public Symbol resolveLocally(SymbolReference reference) {
        return resolve(reference, resolver(Scope.global(), reference));
    }

    private Symbol resolve(SymbolReference reference, Supplier<Symbol> onNotFound) {
        return symbols.stream()
                .filter(symbol -> symbol.isVisibleFor(reference))
                .max(Comparator.comparingInt(Symbol::getPosition))
                .orElseGet(onNotFound);
    }

    private Supplier<Symbol> resolver(Scope scope, SymbolReference reference) {
        return () -> {
            if (scope == null) {
                return null;
            }
            return scope.resolve(reference);
        };
    }

    public static Scope global() {
        return new Scope(null, null);
    }
}