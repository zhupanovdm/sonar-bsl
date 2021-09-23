package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXPRESSION;
import static org.zhupanovdm.bsl.tree.BslTree.Type.REFERENCE;

public class ReferenceExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ReferenceExpression expr = creator.expression(parse("Foo", g.rule(EXPRESSION)).getFirstChild())
                .as(ReferenceExpression.class);

        assertThat(expr.getType()).isEqualTo(REFERENCE);
        assertThat(expr.getName()).isEqualTo("Foo");
        assertThat(expr.getTokens()).hasSize(1);
    }
}