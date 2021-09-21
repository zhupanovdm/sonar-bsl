package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.tree.BslTree;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Postfix extends Expression {
    private Postfix postfix;

    public Postfix(BslTree parent, Token token) {
        super(parent, token);
    }

    public <T extends Postfix> T getPostfix(Class<T> type) {
        return postfix.as(type);
    }
}
