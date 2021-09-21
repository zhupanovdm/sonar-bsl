package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class IfStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void testIf() {
        IfStatement stmt = creator.ifStatement(parse("If Foo Then ; EndIf", g.rule(BslGrammar.IF_STMT)));

        assertThat(stmt.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getElsIfBranches()).isEmpty();
        assertThat(stmt.getElseClause()).isNull();
        assertThat(stmt.getBody()).hasSize(1);
    }

    @Test
    public void testElsIf() {
        IfStatement stmt = creator.ifStatement(parse(
                "If Foo0 Then\n" +
                        "ElsIf Foo1 Then ;\n" +
                        "ElsIf Foo2 Then ;\n" +
                        "Else ;" +
                        "EndIf", g.rule(BslGrammar.IF_STMT)));

        ElsIfBranch elsIfBranch1 = stmt.getElsIfBranches().get(0);
        assertThat(elsIfBranch1.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo1");
        assertThat(elsIfBranch1.getBody()).hasSize(1);

        ElsIfBranch elsIfBranch2 = stmt.getElsIfBranches().get(1);
        assertThat(elsIfBranch2.getCondition().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Foo2");
        assertThat(elsIfBranch2.getBody()).hasSize(1);

        ElseClause elseClause = stmt.getElseClause();
        assertThat(elseClause.getBody()).hasSize(1);
    }
}