package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class ParenthesisExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ParenthesisExpression stmt = creator.expression(parse("(Foo)", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(ParenthesisExpression.class);

        assertThat(stmt.getExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
    }
}