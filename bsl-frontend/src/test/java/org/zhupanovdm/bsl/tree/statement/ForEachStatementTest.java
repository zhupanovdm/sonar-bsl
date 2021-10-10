package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.FOREACH_STMT;
import static org.zhupanovdm.bsl.tree.BslTree.Type.VARIABLE;

public class ForEachStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        ForEachStatement stmt = creator.forEachStmt(parse("For each Foo In Bar Do ; EndDo", g.rule(BslGrammar.FOREACH_STMT)));

        assertThat(stmt.getType()).isEqualTo(FOREACH_STMT);
        assertThat(stmt.getVariable().getType()).isEqualTo(VARIABLE);
        assertThat(stmt.getVariable().getName()).isEqualTo("Foo");
        assertThat(stmt.getCollection().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(stmt.getBody()).hasSize(1);
        assertThat(stmt.getTokens()).hasSize(5);
    }
}