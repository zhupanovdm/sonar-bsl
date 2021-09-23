package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.NEW;

public class NewExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void noArgs() {
        NewExpression stmt = creator.expression(parse("New Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(NewExpression.class);

        assertThat(stmt.getType()).isEqualTo(NEW);
        assertThat(stmt.getName()).isEqualTo("Foo");
        assertThat(stmt.getPostfix()).isNull();
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(2);
    }

    @Test
    public void args() {
        NewExpression stmt = creator.expression(parse("New Foo(A,)", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(NewExpression.class);
        List<BslTree> args = stmt.getPostfix(CallPostfix.class).getArguments();

        assertThat(args.get(0).as(ReferenceExpression.class).getName()).isEqualTo("A");
        assertThat(args.get(1).as(EmptyExpression.class)).isNotNull();
        assertThat(args).hasSize(2);

        assertThat(stmt.getTokens()).hasSize(2);
    }
}