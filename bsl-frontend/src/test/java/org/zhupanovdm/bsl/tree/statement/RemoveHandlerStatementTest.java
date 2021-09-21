package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class RemoveHandlerStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        RemoveHandlerStatement stmt = creator.removeHandlerStmt(parse("RemoveHandler Event, Handler", g.rule(BslGrammar.REMOVE_HANDLER_STMT)));

        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getEvent().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Event");
        assertThat(stmt.getHandler().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Handler");
    }
}