package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.expression.CallPostfix;
import org.zhupanovdm.bsl.tree.expression.DereferencePostfix;
import org.zhupanovdm.bsl.tree.expression.IndexPostfix;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.TestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

public class AssignmentStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void assignmentBasic() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getType()).isEqualTo(ASSIGN_STMT);
        assertThat(stmt.getTarget().getName()).isEqualTo("Foo");
        assertThat(stmt.getTarget().getPostfix()).isNull();
        assertThat(stmt.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(stmt.getBody()).isEmpty();
        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentDereference() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo.A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().getPostfix().getType()).isEqualTo(DEREFERENCE);
        assertThat(stmt.getTarget().getPostfix(DereferencePostfix.class).getName()).isEqualTo("A");
        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentIndex() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().getPostfix().getType()).isEqualTo(INDEX);
        assertThat(stmt.getTarget().getPostfix(IndexPostfix.class).getIndex().as(ReferenceExpression.class).getName()).isEqualTo("A");
        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentCallIndex() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo()[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().getPostfix().getType()).isEqualTo(CALL);
        assertThat(stmt.getTarget()
                .getPostfix(CallPostfix.class)
                .getPostfix(IndexPostfix.class)
                .getIndex().as(ReferenceExpression.class).getName()).isEqualTo("A");
    }

    @Test
    public void assignmentCallDereference() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo().A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().getPostfix().getType()).isEqualTo(CALL);
        assertThat(stmt.getTarget()
                .getPostfix(CallPostfix.class)
                .getPostfix(DereferencePostfix.class).getName()).isEqualTo("A");
    }
}