package org.zhupanovdm.bsl.tree.expression;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class NewExpressionTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void noArgs() {
        NewExpression stmt = creator.expression(parse("New Foo", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(NewExpression.class);

        assertThat(stmt.getObject().getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getPostfix()).isNull();
        assertThat(stmt.getBody()).isEmpty();
    }

    @Test
    public void args() {
        NewExpression stmt = creator.expression(parse("New Foo(A,)", g.rule(BslGrammar.EXPRESSION)).getFirstChild()).as(NewExpression.class);
        List<Expression> args = stmt.getPostfix(CallPostfix.class).getArguments();

        assertThat(args.get(0).as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("A");
        assertThat(args.get(1).as(CallPostfix.DefaultArgument.class)).isNotNull();
        assertThat(args).hasSize(2);
    }
}