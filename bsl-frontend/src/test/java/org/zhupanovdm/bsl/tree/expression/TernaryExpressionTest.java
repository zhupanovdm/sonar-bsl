package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class TernaryExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        TernaryExpression expr = creator.expression(parse("?(Foo, TrueBar, FalseBar)", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(TernaryExpression.class);

        assertThat(expr.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(expr.getTrueExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("TrueBar");
        assertThat(expr.getFalseExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("FalseBar");
        assertThat(expr.getBody()).isEmpty();
    }
}