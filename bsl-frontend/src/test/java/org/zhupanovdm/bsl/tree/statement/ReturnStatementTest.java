package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.RETURN_STMT;

public class ReturnStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void parametrized() {
        ReturnStatement stmt = creator.returnStmt(parse("Return Foo", g.rule(BslGrammar.RETURN_STMT)));

        assertThat(stmt.getType()).isEqualTo(RETURN_STMT);
        assertThat(stmt.getExpression().isPresent()).isTrue();
        assertThat(stmt.getExpression().get().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void noParam() {
        ReturnStatement stmt = creator.returnStmt(parse("Return", g.rule(BslGrammar.RETURN_STMT)));

        assertThat(stmt.getType()).isEqualTo(RETURN_STMT);
        assertThat(stmt.getExpression().isPresent()).isFalse();
    }
}