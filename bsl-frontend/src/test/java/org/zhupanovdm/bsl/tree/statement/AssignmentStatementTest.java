package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.api.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.CallPostfix;
import org.zhupanovdm.bsl.tree.expression.DereferencePostfix;
import org.zhupanovdm.bsl.tree.expression.IndexPostfix;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;

public class AssignmentStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void assignmentBasic() {
        AssignmentStatement stmt = creator.assignmentStmt(parse("Foo = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getReference().getIdentifier().getValue()).isEqualTo("Foo");
        assertThat(stmt.getTarget().getPostfix()).isNull();
        assertThat(stmt.getExpression().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("Bar");
        assertThat(stmt.getBody()).isEmpty();
    }

    @Test
    public void assignmentDereference() {
        AssignmentStatement stmt = creator.assignmentStmt(parse("Foo.A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getPostfix(DereferencePostfix.class).getIdentifier().getValue()).isEqualTo("A");
    }

    @Test
    public void assignmentIndex() {
        AssignmentStatement stmt = creator.assignmentStmt(parse("Foo[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getPostfix(IndexPostfix.class).getIndex().as(ReferenceExpression.class).getIdentifier().getValue())
                .isEqualTo("A");
    }

    @Test
    public void assignmentCallIndex() {
        AssignmentStatement stmt = creator.assignmentStmt(parse("Foo()[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget()
                .getPostfix(CallPostfix.class)
                .getPostfix(IndexPostfix.class)
                .getIndex().as(ReferenceExpression.class).getIdentifier().getValue()).isEqualTo("A");
    }

    @Test
    public void assignmentCallDereference() {
        AssignmentStatement stmt = creator.assignmentStmt(parse("Foo().A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget()
                .getPostfix(CallPostfix.class)
                .getPostfix(DereferencePostfix.class).getIdentifier().getValue()).isEqualTo("A");
    }
}