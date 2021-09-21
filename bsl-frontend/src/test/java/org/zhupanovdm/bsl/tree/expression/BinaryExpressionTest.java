package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class BinaryExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void add() {
        BinaryExpression expr = creator.expression(parse("Foo + Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.ADD);
    }

    @Test
    public void sub() {
        BinaryExpression expr = creator.expression(parse("Foo - Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.SUB);
    }

    @Test
    public void mul() {
        BinaryExpression expr = creator.expression(parse("Foo * Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.MUL);
    }

    @Test
    public void div() {
        BinaryExpression expr = creator.expression(parse("Foo / Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.DIV);
    }

    @Test
    public void mod() {
        BinaryExpression expr = creator.expression(parse("Foo % Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.MOD);
    }

    @Test
    public void and() {
        BinaryExpression expr = creator.expression(parse("Foo And Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.AND);
    }

    @Test
    public void or() {
        BinaryExpression expr = creator.expression(parse("Foo Or Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.OR);
    }

    @Test
    public void eq() {
        BinaryExpression expr = creator.expression(parse("Foo = Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.EQ);
    }

    @Test
    public void gt() {
        BinaryExpression expr = creator.expression(parse("Foo > Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.GT);
    }

    @Test
    public void ge() {
        BinaryExpression expr = creator.expression(parse("Foo >= Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.GE);
    }

    @Test
    public void lt() {
        BinaryExpression expr = creator.expression(parse("Foo < Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.LT);
    }

    @Test
    public void le() {
        BinaryExpression expr = creator.expression(parse("Foo <= Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.LE);
    }

    @Test
    public void neq() {
        BinaryExpression expr = creator.expression(parse("Foo <> Bar", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(BinaryExpression.class);

        assertThat(expr.getLeft().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getRight().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(expr.getOperator().getValue()).isEqualTo(BinaryOperator.Type.NEQ);
    }

}