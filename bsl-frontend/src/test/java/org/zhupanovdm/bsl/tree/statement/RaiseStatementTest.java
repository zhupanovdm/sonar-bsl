package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.RAISE_STMT;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class RaiseStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void parametrized() {
        RaiseStatement stmt = creator.raiseStmt(parse("Raise Foo", g.rule(BslGrammar.RAISE_STMT)));

        assertThat(stmt.getType()).isEqualTo(RAISE_STMT);
        assertThat(stmt.getExpression().get().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void noParam() {
        RaiseStatement stmt = creator.raiseStmt(parse("Raise", g.rule(BslGrammar.RAISE_STMT)));

        assertThat(stmt.getType()).isEqualTo(RAISE_STMT);
        assertThat(stmt.getExpression().isPresent()).isFalse();
    }
}