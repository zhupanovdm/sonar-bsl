package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.tree.BslTree;

import java.util.Optional;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Postfix extends BslTree {
    private Postfix postfix;

    public Postfix(BslTree parent, Type type) {
        super(parent, type);
    }

    public Optional<Postfix> getPostfix() {
        return Optional.ofNullable(postfix);
    }

    public <T extends Postfix> Optional<T> getPostfix(Class<T> type) {
        return getPostfix().map(p -> p.as(type));
    }
}
