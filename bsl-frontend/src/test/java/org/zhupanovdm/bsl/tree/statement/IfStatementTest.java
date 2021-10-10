package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.IF_STMT;

public class IfStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void testIf() {
        IfStatement stmt = creator.ifStmt(parse("If Foo Then ; EndIf", g.rule(BslGrammar.IF_STMT)));

        assertThat(stmt.getType()).isEqualTo(IF_STMT);
        assertThat(stmt.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getElsIfBranches()).isEmpty();
        assertThat(stmt.getElseClause().isPresent()).isFalse();
        assertThat(stmt.getBody()).hasSize(1);
        assertThat(stmt.getTokens()).hasSize(3);
    }

    @Test
    public void testElsIf() {
        IfStatement stmt = creator.ifStmt(parse(
                "If Foo0 Then\n" +
                        "ElsIf Foo1 Then ;\n" +
                        "ElsIf Foo2 Then ;\n" +
                        "Else ;" +
                        "EndIf", g.rule(BslGrammar.IF_STMT)));

        assertThat(stmt.getTokens()).hasSize(3);

        ElsIfBranch elsIfBranch1 = stmt.getElsIfBranches().get(0);
        assertThat(elsIfBranch1.getType()).isEqualTo(IF_STMT);
        assertThat(elsIfBranch1.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo1");
        assertThat(elsIfBranch1.getBody()).hasSize(1);
        assertThat(elsIfBranch1.getTokens()).hasSize(2);

        ElsIfBranch elsIfBranch2 = stmt.getElsIfBranches().get(1);
        assertThat(elsIfBranch2.getCondition().as(ReferenceExpression.class).getName()).isEqualTo("Foo2");
        assertThat(elsIfBranch2.getBody()).hasSize(1);
        assertThat(elsIfBranch2.getTokens()).hasSize(2);

        assertThat(stmt.getElseClause().isPresent()).isTrue();

        ElseClause elseClause = stmt.getElseClause().get();
        assertThat(elseClause.getType()).isEqualTo(IF_STMT);
        assertThat(elseClause.getBody()).hasSize(1);
        assertThat(elseClause.getTokens()).hasSize(1);
    }
}