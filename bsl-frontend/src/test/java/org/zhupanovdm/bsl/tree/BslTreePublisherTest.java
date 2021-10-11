package org.zhupanovdm.bsl.tree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.zhupanovdm.bsl.tree.definition.*;
import org.zhupanovdm.bsl.tree.expression.CallPostfix;
import org.zhupanovdm.bsl.tree.expression.EmptyExpression;
import org.zhupanovdm.bsl.tree.expression.PrimitiveExpression;
import org.zhupanovdm.bsl.tree.expression.ReferenceExpression;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;
import org.zhupanovdm.bsl.tree.statement.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.zhupanovdm.bsl.ParserTestUtils.module;

@RunWith(MockitoJUnitRunner.class)
public class BslTreePublisherTest {

    private BslTreePublisher publisher;

    @Mock
    private BslTreeSubscriber subscriber;

    @Before
    public void setup() {
        publisher = new BslTreePublisher();
        publisher.subscribe(subscriber);
    }

    @Test
    public void scanTree() {
        ModuleRoot root = module("");
        publisher.scan(root);

        verify(subscriber).init();
        verify(subscriber).onEnterNode(eq(root));
        verify(subscriber).onVisitModule(eq(root));
        verify(subscriber).onLeaveNode(eq(root));
    }

    @Test
    public void scanPreprocessor() {
        publisher.scan(module("#If Server Then Foo() #ElsIf Client Then Bar() #EndIf"));

        // #If Server Then ...
        verify(subscriber).onVisitPreprocessorIf(any(PreprocessorIf.class));

        // #ElsIf Client Then ...
        verify(subscriber).onVisitPreprocessorElsif(any(PreprocessorElsif.class));

        // Server, Client, Foo, Bar
        verify(subscriber, times(4)).onVisitReferenceExpression(any(ReferenceExpression.class));

        // Foo(), Bar()
        verify(subscriber, times(2)).onVisitCallStatement(any(CallStatement.class));
    }

    @Test
    public void scanFunction() {
        publisher.scan(module("&AtServer Function Foo(Bar, Baz=1) Return Bar EndFunction"));

        // &AtServer
        verify(subscriber).onVisitDirective(any(Directive.class));

        // Function Foo(Bar, Baz=1) ...
        verify(subscriber).onVisitCallableDefinition(any(CallableDefinition.class));

        // Bar, Baz=1
        verify(subscriber, times(2)).onVisitParameter(any(Parameter.class));

        // 1
        verify(subscriber).onVisitPrimitiveExpression(any(PrimitiveExpression.class));

        // Return Bar
        verify(subscriber).onVisitReturnStatement(any(ReturnStatement.class));

        // Bar
        verify(subscriber).onVisitReferenceExpression(any(ReferenceExpression.class));
    }

    @Test
    public void scanProcedure() {
        publisher.scan(module("&AtServer Procedure Foo(Bar, Baz=1) Return EndProcedure"));

        // &AtServer
        verify(subscriber).onVisitDirective(any(Directive.class));

        // Procedure Foo(Bar, Baz=1) ...
        verify(subscriber).onVisitCallableDefinition(any(CallableDefinition.class));

        // Bar, Baz=1
        verify(subscriber, times(2)).onVisitParameter(any(Parameter.class));

        // 1
        verify(subscriber).onVisitPrimitiveExpression(any(PrimitiveExpression.class));

        // Return
        verify(subscriber).onVisitReturnStatement(any(ReturnStatement.class));
    }

    @Test
    public void scanVar() {
        publisher.scan(module("&AtServer Var Foo, Bar;"));

        // &AtServer
        verify(subscriber).onVisitDirective(any(Directive.class));

        // Var Foo, Bar;
        verify(subscriber).onVisitVariablesDefinition(any(VariablesDefinition.class));

        // Foo, Bar
        verify(subscriber, times(2)).onVisitVariable(any(Variable.class));
    }

    @Test
    public void scanAssignmentStmt() {
        publisher.scan(module("Foo = Bar"));

        // Foo
        verify(subscriber).onVisitVariable(any(Variable.class));

        // Foo = Bar
        verify(subscriber).onVisitAssignmentStatement(any(AssignmentStatement.class));

        // Bar
        verify(subscriber).onVisitReferenceExpression(any(ReferenceExpression.class));
    }

    @Test
    public void scanCallStmt() {
        publisher.scan(module("Foo(Bar, Baz,,)"));

        // Foo(Bar, Baz,,)
        verify(subscriber).onVisitCallStatement(any(CallStatement.class));

        // (Bar, Baz,,)
        verify(subscriber).onVisitCallPostfix(any(CallPostfix.class));

        // Foo, Bar, Baz
        verify(subscriber, times(3)).onVisitReferenceExpression(any(ReferenceExpression.class));

        verify(subscriber, times(2)).onVisitEmptyExpression(any(EmptyExpression.class));
    }

    @Test
    public void scanIfStmt() {
        publisher.scan(module("If True Then Foo() ElsIf False Then Bar() Else Baz() EndIf"));

        // If True Then ...
        verify(subscriber).onVisitIfStatement(any(IfStatement.class));

        // ElsIf False Then ...
        verify(subscriber).onVisitElsIfBranch(any(ElsIfBranch.class));

        // Else ...
        verify(subscriber).onVisitElseClause(any(ElseClause.class));

        // Foo(), Bar(), Baz()
        verify(subscriber, times(3)).onVisitCallStatement(any(CallStatement.class));

        // True, False
        verify(subscriber, times(2)).onVisitPrimitiveExpression(any(PrimitiveExpression.class));
    }

    @Test
    public void scanWhileStmt() {
        publisher.scan(module("While True Do Continue; Break EndDo"));

        // While True Do ...
        verify(subscriber).onVisitWhileStatement(any(WhileStatement.class));

        // True
        verify(subscriber).onVisitPrimitiveExpression(any(PrimitiveExpression.class));

        // Continue
        verify(subscriber).onVisitContinueStatement(any(ContinueStatement.class));

        // Break
        verify(subscriber).onVisitBreakStatement(any(BreakStatement.class));
    }

    @Test
    public void scanForStmt() {
        publisher.scan(module("For N = 0 To 10 Do Continue; Break EndDo"));

        // For N = 0 To 10 Do ...
        verify(subscriber).onVisitForStatement(any(ForStatement.class));

        // N
        verify(subscriber).onVisitVariable(any(Variable.class));

        // 0, 10
        verify(subscriber, times(2)).onVisitPrimitiveExpression(any(PrimitiveExpression.class));

        // Continue
        verify(subscriber).onVisitContinueStatement(any(ContinueStatement.class));

        // Break
        verify(subscriber).onVisitBreakStatement(any(BreakStatement.class));
    }

    @Test
    public void scanForEachStmt() {
        publisher.scan(module("For each Elem In Collection Do Continue; Break EndDo"));

        // For each Elem In Collection Do ...
        verify(subscriber).onVisitForEachStatement(any(ForEachStatement.class));

        // Elem
        verify(subscriber).onVisitVariable(any(Variable.class));

        // Collection
        verify(subscriber).onVisitReferenceExpression(any(ReferenceExpression.class));

        // Continue
        verify(subscriber).onVisitContinueStatement(any(ContinueStatement.class));

        // Break
        verify(subscriber).onVisitBreakStatement(any(BreakStatement.class));
    }

    @Test
    public void scanRaiseStmt() {
        publisher.scan(module("Raise Exception"));

        // Raise Exception ...
        verify(subscriber).onVisitRaiseStatement(any(RaiseStatement.class));

        // Exception
        verify(subscriber).onVisitReferenceExpression(any(ReferenceExpression.class));
    }

    @Test
    public void scanEmptyStmt() {
        publisher.scan(module(";"));

        // ;
        verify(subscriber).onVisitEmptyStatement(any(EmptyStatement.class));
    }

    @Test
    public void scanExceptStmt() {
        publisher.scan(module("Try Foo() Except Bar() EndTry"));

        // Try Foo() ...
        verify(subscriber).onVisitTryStatement(any(TryStatement.class));

        // Except Bar() ...
        verify(subscriber).onVisitExceptClause(any(ExceptClause.class));

        // Foo(), Bar()
        verify(subscriber, times(2)).onVisitCallStatement(any(CallStatement.class));
    }

    @Test
    public void scanExecuteStmt() {
        publisher.scan(module("Execute(Expr)"));

        // Execute(Expr)
        verify(subscriber).onVisitExecuteStatement(any(ExecuteStatement.class));

        // Expr
        verify(subscriber).onVisitReferenceExpression(any(ReferenceExpression.class));
    }

    @Test
    public void scanGotoStmt() {
        publisher.scan(module("Goto ~Label"));

        // Goto ~Label
        verify(subscriber).onVisitGotoStatement(any(GotoStatement.class));

        // ~Label
        verify(subscriber).onVisitLabel(any(Label.class));
    }

    @Test
    public void scanLabelDef() {
        publisher.scan(module("~Label:"));

        // ~Label:
        verify(subscriber).onVisitLabelDefinition(any(LabelDefinition.class));

        // ~Label
        verify(subscriber).onVisitLabel(any(Label.class));
    }
}