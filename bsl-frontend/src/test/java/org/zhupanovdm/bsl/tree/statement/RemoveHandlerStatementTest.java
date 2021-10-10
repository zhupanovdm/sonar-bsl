package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.REM_HANDLER_STMT;

public class RemoveHandlerStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        RemoveHandlerStatement stmt = creator.removeHandlerStmt(parse("RemoveHandler Event, Handler", g.rule(BslGrammar.REMOVE_HANDLER_STMT)));

        assertThat(stmt.getType()).isEqualTo(REM_HANDLER_STMT);
        assertThat(stmt.getEvent().as(ReferenceExpression.class).getName()).isEqualTo("Event");
        assertThat(stmt.getHandler().as(ReferenceExpression.class).getName()).isEqualTo("Handler");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(2);
    }
}