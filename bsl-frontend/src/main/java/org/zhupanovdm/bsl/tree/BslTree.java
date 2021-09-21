package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "parent")
public abstract class BslTree {
    private BslTree parent;
    private Token token;
    private final List<BslTree> body = new LinkedList<>();

    public BslTree(BslTree parent, Token token) {
        this.parent = parent;
        this.token = token;
    }

    public abstract void accept(BslTreeVisitor visitor);

    public <T extends BslTree> T as(Class<T> type) {
        return type.cast(this);
    }

    public boolean is(Class<? extends BslTree> type) {
        return type.isAssignableFrom(getClass());
    }
}