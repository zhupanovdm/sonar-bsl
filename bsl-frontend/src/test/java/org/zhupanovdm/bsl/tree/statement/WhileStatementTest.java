package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.WHILE_STMT;

public class WhileStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void test() {
        WhileStatement stmt = creator.whileStmt(parse("While Foo Do ; EndDo", g.rule(BslGrammar.WHILE_STMT)));

        assertThat(stmt.getType()).isEqualTo(WHILE_STMT);
        assertThat(stmt.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getBody()).hasSize(1);
        assertThat(stmt.getTokens()).hasSize(3);
    }
}