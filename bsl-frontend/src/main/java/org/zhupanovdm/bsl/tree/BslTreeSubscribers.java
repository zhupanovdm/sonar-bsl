package org.zhupanovdm.bsl.tree;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BslTreeSubscribers {
    private final List<BslTreeSubscriber> subscribers = new LinkedList<>();

    public BslTreeSubscribers(BslTreeSubscriber ...subscribers) {
        this.subscribers.addAll(Arrays.asList(subscribers));
    }

    public BslTreeSubscribers add(BslTreeSubscriber ...subscribers) {
        return add(Arrays.asList(subscribers));
    }

    public BslTreeSubscribers add(Collection<? extends BslTreeSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
        return this;
    }

    public void accept(Consumer<BslTreeSubscriber> consumer) {
        subscribers.forEach(consumer);
    }
}
