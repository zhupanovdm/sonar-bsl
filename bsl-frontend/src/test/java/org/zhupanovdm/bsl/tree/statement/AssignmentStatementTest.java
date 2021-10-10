package org.zhupanovdm.bsl.tree.statement;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.bsl.grammar.BslGrammar;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.definition.Variable;
import org.zhupanovdm.bsl.tree.expression.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zhupanovdm.bsl.ParserTestUtils.parse;
import static org.zhupanovdm.bsl.tree.BslTree.Type.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class AssignmentStatementTest {
    private final LexerlessGrammar g = BslGrammar.create();
    private final BslTreeCreator creator = new BslTreeCreator();

    @Test
    public void assignmentBasic() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getType()).isEqualTo(ASSIGN_STMT);
        assertThat(stmt.getTarget().getType()).isEqualTo(VARIABLE);
        assertThat(stmt.getTarget().as(Variable.class).getName()).isEqualTo("Foo");
        assertThat(stmt.getExpression().as(ReferenceExpression.class).getName()).isEqualTo("Bar");
        assertThat(stmt.getBody()).isEmpty();

        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentDereference() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo.A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().as(PostfixExpression.class).getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix().get().getType()).isEqualTo(DEREFERENCE);
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix(DereferencePostfix.class).get().getName()).isEqualTo("A");

        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentIndex() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().as(PostfixExpression.class).getReference().getName()).isEqualTo("Foo");

        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix().isPresent()).isTrue();
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix().get().getType()).isEqualTo(INDEX);

        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix(IndexPostfix.class).isPresent()).isTrue();
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix(IndexPostfix.class).get().getIndex().as(ReferenceExpression.class).getName()).isEqualTo("A");

        assertThat(stmt.getTokens()).hasSize(1);
    }

    @Test
    public void assignmentCallIndex() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo()[A] = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().as(PostfixExpression.class).getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix().get().getType()).isEqualTo(CALL);
        assertThat(stmt.getTarget().as(PostfixExpression.class)
                .getPostfix(CallPostfix.class).get()
                .getPostfix(IndexPostfix.class).get()
                .getIndex().as(ReferenceExpression.class).getName()).isEqualTo("A");
    }

    @Test
    public void assignmentCallDereference() {
        AssignmentStatement stmt = creator.assignStmt(parse("Foo().A = Bar", g.rule(BslGrammar.ASSIGN_STMT)));

        assertThat(stmt.getTarget().getType()).isEqualTo(POSTFIX);
        assertThat(stmt.getTarget().as(PostfixExpression.class).getReference().getName()).isEqualTo("Foo");
        assertThat(stmt.getTarget().as(PostfixExpression.class).getPostfix().get().getType()).isEqualTo(CALL);
        assertThat(stmt.getTarget().as(PostfixExpression.class)
                .getPostfix(CallPostfix.class).get()
                .getPostfix(DereferencePostfix.class).get()
                .getName()).isEqualTo("A");
    }
}