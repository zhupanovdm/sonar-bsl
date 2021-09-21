package org.zhupanovdm.bsl.tree.expression;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class PostfixExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void dereference() {
        PostfixExpression stmt = creator.expression(parse("Foo.Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getReference().getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getPostfix(DereferencePostfix.class).getIdentifier().getValue()).isEqualTo("Bar");
    }

    @Test
    public void index() {
        PostfixExpression stmt = creator.expression(parse("Foo[Bar]", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getReference().getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getPostfix(IndexPostfix.class).getIndex().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
    }

    @Test
    public void call() {
        PostfixExpression stmt = creator.expression(parse("Foo()", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getReference().getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getPostfix(CallPostfix.class).getArguments()).isEmpty();
    }

    @Test
    public void callArgs() {
        PostfixExpression stmt = creator.expression(parse("Foo(A,)", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(PostfixExpression.class);

        List<Expression> args = stmt.getPostfix(CallPostfix.class).getArguments();
        assertThat(args.get(0).as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("A");
        assertThat(args.get(1).as(CallPostfix.DefaultArgument.class)).isNotNull();
        assertThat(args).hasSize(2);
    }

    @Test
    public void complex() {
        AstNode tree = parse("Foo.Bar[A].B()", g.rule(BslGrammar.EXPRESSION));
        PostfixExpression stmt = creator.expression(tree.getFirstChild()).as(PostfixExpression.class);

        assertThat(stmt.getPostfix(DereferencePostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class)
                .getPostfix(IndexPostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class)
                .getPostfix(IndexPostfix.class)
                .getPostfix(DereferencePostfix.class)).isNotNull();
        assertThat(stmt.getPostfix(DereferencePostfix.class)
                .getPostfix(IndexPostfix.class)
                .getPostfix(DereferencePostfix.class)
                .getPostfix(CallPostfix.class)).isNotNull();
    }

}