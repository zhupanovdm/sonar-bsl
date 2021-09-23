package org.zhupanovdm.bsl.tree;

import java.util.*;
import java.util.function.Consumer;

public abstract class BslTreeSubscriber {
    protected final Map<BslTree.Type, List<Consumer<BslTree>>> subscribers = new HashMap<>();

    public void scan(BslTree tree) {
        if (tree == null) {
            return;
        }
        publish(tree);
        for (BslTree child : tree.getBody()) {
            publish(child);
        }
    }

    protected void publish(BslTree tree) {
        for (Consumer<BslTree> subscriber :
                subscribers.getOrDefault(tree.getType(), Collections.emptyList())) {
            subscriber.accept(tree);
        }
    }

    public void register(Consumer<BslTree> subscriber, BslTree.Type ...types) {
        for (BslTree.Type type : types) {
            subscribers.computeIfAbsent(type, t -> new LinkedList<>()).add(subscriber);
        }
    }
}
