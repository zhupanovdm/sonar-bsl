package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.grammar.BslGrammar.EXPRESSION;
import static org.zhupanovdm.bsl.tree.BslTree.Type.PARENTHESIS;

public class ParenthesisExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ParenthesisExpression stmt = creator.expression(parse("(Foo)", g.rule(EXPRESSION)).getFirstChild()).as(ParenthesisExpression.class);

        assertThat(stmt.getType()).isEqualTo(PARENTHESIS);
        assertThat(stmt.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getTokens()).hasSize(2);
    }
}