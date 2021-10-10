package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXPRESSION;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class PostfixExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void dereference() {
        PostfixExpression stmt = creator.expression(parse("Foo.Bar", g.rule(EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTokens()).isEmpty();

        assertThat(stmt.getPostfix().get().getType()).isEqualTo(DEREFERENCE);
        assertThat(stmt.getPostfix(DereferencePostfix.class).get().getName()).isEqualTo("Bar");
        assertThat(stmt.getPostfix().get().getTokens()).hasSize(2);
    }

    @Test
    public void index() {
        PostfixExpression stmt = creator.expression(parse("Foo[Bar]", g.rule(EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTokens()).isEmpty();

        assertThat(stmt.getPostfix().get().getType()).isEqualTo(INDEX);
        assertThat(stmt.getPostfix(IndexPostfix.class).get().getIndex().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(stmt.getPostfix().get().getTokens()).hasSize(2);
    }

    @Test
    public void call() {
        PostfixExpression stmt = creator.expression(parse("Foo()", g.rule(EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTokens()).isEmpty();

        assertThat(stmt.getPostfix().get().getType()).isEqualTo(CALL);
        assertThat(stmt.getPostfix(CallPostfix.class).get().getArguments()).isEmpty();
        assertThat(stmt.getPostfix().get().getTokens()).hasSize(2);
    }

    @Test
    public void callArgs() {
        PostfixExpression stmt = creator.expression(parse("Foo(A,)", g.rule(EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        List<BslTree> args = stmt.getPostfix(CallPostfix.class).get().getArguments();
        assertThat(args.get(0).as(ReferenceExpression.class).getName()).isEqualTo("A");
        assertThat(args.get(1).as(EmptyExpression.class)).isNotNull();
        assertThat(args).hasSize(2);
    }

    @Test
    public void complex() {
        AstNode tree = parse("Foo.Bar[A].B()", g.rule(EXPRESSION));
        PostfixExpression stmt = creator.expression(tree.getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getPostfix(DereferencePostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class).get()
                .getPostfix(IndexPostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class).get()
                .getPostfix(IndexPostfix.class).get()
                .getPostfix(DereferencePostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class).get()
                .getPostfix(IndexPostfix.class).get()
                .getPostfix(DereferencePostfix.class).get()
                .getPostfix(CallPostfix.class)).isNotNull();
    }
}