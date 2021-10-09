package org.zhupanovdm.bsl.tree.expression;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.util.LinkedList;
import java.util.List;

import static org.zhupanovdm.bsl.tree.BslTree.Type.CALL;
import static org.zhupanovdm.bsl.utils.StringUtils.collectionToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class CallPostfix extends Postfix {
    private final List<BslTree> arguments = new LinkedList<>();

    public CallPostfix(BslTree parent) {
        super(parent, CALL);
    }

    @Override
    public void accept(BslTreeSubscriber subscriber) {
        subscriber.onVisitCallPostfix(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append('(');
        collectionToString(builder, arguments, BslTree::toString, ", ");
        builder.append(')');

        getPostfix().ifPresent(builder::append);
        return builder.toString();
    }
}
