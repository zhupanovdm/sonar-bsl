package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.ADD_HANDLER_STMT;

public class AddHandlerStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        AddHandlerStatement stmt = creator.addHandlerStmt(parse("AddHandler Event, Handler", g.rule(BslGrammar.ADD_HANDLER_STMT)));

        assertThat(stmt.getType()).isEqualTo(ADD_HANDLER_STMT);
        assertThat(stmt.getEvent().as(ReferenceExpression.class).getName()).isEqualTo("Event");
        assertThat(stmt.getHandler().as(ReferenceExpression.class).getName()).isEqualTo("Handler");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(2);
    }
}