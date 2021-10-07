package org.zhupanovdm.bsl.tree;

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
    private Type type;
    private final List<BslToken> tokens = new LinkedList<>();
    private final List<BslTree> body = new LinkedList<>();

    public BslTree(BslTree parent, Type type) {
        this.parent = parent;
        this.type = type;
    }

    public abstract void accept(BslTreeSubscriber subscriber);

    public <T extends BslTree> T as(Class<T> type) {
        return type.cast(this);
    }

    public boolean is(Type ...types) {
        for (Type t : types) {
            if (type == t) {
                return true;
            }
        }
        return false;
    }

    public boolean isChildOf(BslTree node) {
        if (parent == null) {
            return false;
        }
        if (parent.equals(node)) {
            return true;
        }
        return parent.isChildOf(node);
    }

    public boolean isParentOf(BslTree node) {
        if (node == null) {
            return false;
        }
        if (node.parent != null && node.parent.equals(this)) {
            return true;
        }
        return isParentOf(node.parent);
    }

    public void addToken(BslToken token) {
        tokens.add(token);
    }

    public BslToken getFirstToken() {
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    public enum Type {
        MODULE, PREPROCESSOR,

        DIRECTIVE,
        FUNCTION, PROCEDURE, PARAMETER,
        VAR_DEF, VARIABLE,
        LABEL_DEF, LABEL,

        ASSIGN_STMT,
        IF_STMT,
        WHILE_STMT,
        FOR_STMT,
        FOREACH_STMT,
        TRY_STMT,
        CALL_STMT,
        RETURN_STMT,
        BREAK_STMT,
        CONTINUE_STMT,
        EMPTY_STMT,
        GOTO_STMT,
        EXECUTE_STMT,
        RAISE_STMT,
        ADD_HANDLER_STMT,
        REM_HANDLER_STMT,

        POSTFIX, INDEX, DEREFERENCE, CALL,
        EMPTY,
        TERNARY,
        NEW,

        ADD, SUB, MUL, DIV, MOD,
        AND, OR, NOT,
        MINUS,
        EQ, NEQ, GT, GE, LT, LE,

        REFERENCE, PARENTHESIS,

        STRING, NUMBER, DATE, TRUE, FALSE, UNDEFINED, NULL
    }
}