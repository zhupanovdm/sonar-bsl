package org.zhupanovdm.bsl.utils;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class AstSiblingsCursor implements Iterator<AstNode>, Iterable<AstNode> {
    private AstNode node;

    public AstSiblingsCursor(AstNode node) {
        this.node = node;
    }

    @Override
    public Iterator<AstNode> iterator() {
        return this;
    }

    @Override
    public AstNode next() {
        AstNode node = this.node;
        this.node = this.node.getNextSibling();
        return node;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    public AstSiblingsCursor descend() {
        return new AstSiblingsCursor(next().getFirstChild());
    }

    public boolean check(AstNodeType... types) {
        return node.is(types);
    }

    public boolean checkThenNext(AstNodeType... types) {
        if (check(types)) {
            next();
            return true;
        }
        return false;
    }

    public <T> T map(Function<AstNode, T> mapper, AstNodeType... types) {
        if (types.length == 0 || check(types)) {
            return mapper.apply(next());
        }
        return null;
    }

    public AstNode optional(AstNodeType... types) {
        if (types.length == 0 || check(types)) {
            return next();
        }
        return null;
    }

    public boolean consume(Consumer<AstNode> consumer, AstNodeType... types) {
        if (types.length == 0 || check(types)) {
            consumer.accept(next());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + node;
    }
}