package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.MINUS;
import static org.zhupanovdm.bsl.tree.BslTree.Type.NOT;

public class UnaryExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void minus() {
        UnaryExpression expr = creator.expression(parse("-Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(UnaryExpression.class);

        assertThat(expr.getType()).isEqualTo(MINUS);
        assertThat(expr.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }

    @Test
    public void not() {
        UnaryExpression expr = creator.expression(parse("not Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild())
                .as(UnaryExpression.class);

        assertThat(expr.getType()).isEqualTo(NOT);
        assertThat(expr.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(expr.getBody()).isEmpty();
        assertThat(expr.getTokens()).hasSize(1);
    }
}