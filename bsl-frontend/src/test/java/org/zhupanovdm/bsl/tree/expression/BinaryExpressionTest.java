package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXPRESSION;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class BinaryExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void add() {
        BinaryExpression expr = creator.expression(parse("Foo + Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(ADD);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void sub() {
        BinaryExpression expr = creator.expression(parse("Foo - Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(SUB);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void mul() {
        BinaryExpression expr = creator.expression(parse("Foo * Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(MUL);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void div() {
        BinaryExpression expr = creator.expression(parse("Foo / Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(DIV);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void mod() {
        BinaryExpression expr = creator.expression(parse("Foo % Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(MOD);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void and() {
        BinaryExpression expr = creator.expression(parse("Foo And Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(AND);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void or() {
        BinaryExpression expr = creator.expression(parse("Foo Or Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(OR);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void eq() {
        BinaryExpression expr = creator.expression(parse("Foo = Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(EQ);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void gt() {
        BinaryExpression expr = creator.expression(parse("Foo > Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(GT);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void ge() {
        BinaryExpression expr = creator.expression(parse("Foo >= Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(GE);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void lt() {
        BinaryExpression expr = creator.expression(parse("Foo < Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(LT);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void le() {
        BinaryExpression expr = creator.expression(parse("Foo <= Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(LE);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void neq() {
        BinaryExpression expr = creator.expression(parse("Foo <> Bar", g.rule(EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getType()).isEqualTo(NEQ);
        assertThat(expr.getLeft().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }
}