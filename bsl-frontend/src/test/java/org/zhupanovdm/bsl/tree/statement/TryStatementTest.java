package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.TRY_STMT;

public class TryStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        TryStatement stmt = creator.tryStmt(parse("Try Foo() Except Bar() EndTry", g.rule(BslGrammar.TRY_STMT)));

        assertThat(stmt.getType()).isEqualTo(TRY_STMT);
        assertThat(stmt.getBody().get(0).as(CallStatement.class).getExpression().getName()).isEqualTo("Foo");
        assertThat(stmt.getExceptClause().getBody().get(0).as(CallStatement.class).getExpression().getName()).isEqualTo("Bar");
        assertThat(stmt.getTokens()).hasSize(3);
    }
}