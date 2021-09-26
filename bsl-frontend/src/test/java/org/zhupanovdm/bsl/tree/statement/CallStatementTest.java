package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.CallPostfix;
import org.zhupanovdm.bsl.tree.expression.EmptyExpression;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.CALL;
import static org.zhupanovdm.bsl.tree.BslTree.Type.POSTFIX;

public class CallStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void basic() {
        CallStatement stmt = creator.callStmt(parse("Foo()", g.rule(BslGrammar.CALL_STMT)));

        assertThat(stmt.getExpression().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getExpression().getPostfix().getType()).isEqualTo(CALL);
        assertThat(stmt.getExpression().getPostfix()).isInstanceOf(CallPostfix.class);
        assertThat(stmt.getExpression().getPostfix(CallPostfix.class).getArguments()).isEmpty();
        assertThat(stmt.getExpression().getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).isEmpty();
    }

    @Test
    public void args() {
        CallStatement stmt = creator.callStmt(parse("Foo(A, B, C)", g.rule(BslGrammar.CALL_STMT)));
        CallPostfix postfix = stmt.getExpression().getPostfix(CallPostfix.class);

        assertThat(postfix.getArguments().stream().map(e -> e.as(ReferenceExpression.class).getName())).containsExactly("A", "B", "C");
        assertThat(stmt.getTokens()).isEmpty();
    }

    @Test
    public void defaultArgs() {
        CallStatement stmt;
        CallPostfix postfix;

        stmt = creator.callStmt(parse("Foo(,)", g.rule(BslGrammar.CALL_STMT)));
        postfix = stmt.getExpression().getPostfix(CallPostfix.class);

        assertThat(postfix.getArguments().get(0)).isInstanceOf(EmptyExpression.class);
        assertThat(postfix.getArguments().get(1)).isInstanceOf(EmptyExpression.class);

        stmt = creator.callStmt(parse("Foo(A,)", g.rule(BslGrammar.CALL_STMT)));
        postfix = stmt.getExpression().getPostfix(CallPostfix.class);

        assertThat(postfix.getArguments().get(0)).isInstanceOf(ReferenceExpression.class);
        assertThat(postfix.getArguments().get(1)).isInstanceOf(EmptyExpression.class);

        stmt = creator.callStmt(parse("Foo(, A)", g.rule(BslGrammar.CALL_STMT)));
        postfix = stmt.getExpression().getPostfix(CallPostfix.class);

        assertThat(postfix.getArguments().get(0)).isInstanceOf(EmptyExpression.class);
        assertThat(postfix.getArguments().get(1)).isInstanceOf(ReferenceExpression.class);

        stmt = creator.callStmt(parse("Foo(,,)", g.rule(BslGrammar.CALL_STMT)));
        postfix = stmt.getExpression().getPostfix(CallPostfix.class);

        assertThat(postfix.getArguments().get(0)).isInstanceOf(EmptyExpression.class);
        assertThat(postfix.getArguments().get(1)).isInstanceOf(EmptyExpression.class);
        assertThat(postfix.getArguments().get(2)).isInstanceOf(EmptyExpression.class);
    }
}