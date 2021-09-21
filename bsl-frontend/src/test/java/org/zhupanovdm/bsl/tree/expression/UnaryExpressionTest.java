package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class UnaryExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void minus() {
        UnaryExpression expr = creator.expression(parse("-Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(UnaryExpression.class);

        assertThat(expr.getExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getOperator().getValue()).isEqualTo(UnaryOperator.Type.MINUS);
        assertThat(expr.getBody()).isEmpty();
    }

    @Test
    public void not() {
        UnaryExpression expr = creator.expression(parse("not Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(UnaryExpression.class);

        assertThat(expr.getExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getOperator().getValue()).isEqualTo(UnaryOperator.Type.NOT);
        assertThat(expr.getBody()).isEmpty();
    }
}