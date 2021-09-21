package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.api.BslDirective;
import org.zhupanovdm.bsl.tree.definition.*;
import org.zhupanovdm.bsl.tree.expression.*;
import org.zhupanovdm.bsl.tree.module.Module;
import org.zhupanovdm.bsl.tree.module.PreprocessorElsif;
import org.zhupanovdm.bsl.tree.module.PreprocessorIf;
import org.zhupanovdm.bsl.tree.statement.*;
import org.zhupanovdm.bsl.utils.AstSiblingsCursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.zhupanovdm.bsl.api.BslGrammar.*;
import static org.zhupanovdm.bsl.api.BslKeyword.*;
import static org.zhupanovdm.bsl.api.BslPunctuator.*;

public class BslTreeCreator {
    private final Map<AstNodeType, Function<AstNode, Statement>> stmtRules = new HashMap<>();

    public BslTreeCreator() {
        stmtRules.put(ASSIGN_STMT,      this::assignmentStmt);
        stmtRules.put(IF_STMT,          this::ifStatement);
        stmtRules.put(WHILE_STMT,       this::whileStmt);
        stmtRules.put(FOR_STMT,         this::forStmt);
        stmtRules.put(FOREACH_STMT,     this::forEachStmt);
        stmtRules.put(TRY_STMT,         this::tryStmt);
        stmtRules.put(CALL_STMT,        this::callStmt);
        stmtRules.put(RETURN_STMT,      this::returnStmt);
        stmtRules.put(BREAK_STMT,       this::breakStmt);
        stmtRules.put(CONTINUE_STMT,    this::continueStmt);
        stmtRules.put(RAISE_STMT,       this::raiseStmt);
        stmtRules.put(EXECUTE_STMT,     this::executeStmt);
        stmtRules.put(ADD_HANDLER_STMT, this::addHandlerStmt);
        stmtRules.put(REMOVE_HANDLER_STMT,this::removeHandlerStmt);
        stmtRules.put(LABEL_DEF,        this::labelDef);
        stmtRules.put(GOTO_STMT,        this::gotoStmt);
        stmtRules.put(EMPTY_STMT,       this::emptyStmt);
    }

    public Module create(AstNode tree) {
        if (!tree.is(MODULE)) {
            throw new IllegalArgumentException("Is not BSL module AST");
        }
        Module module = new Module();
        body(module, tree.getFirstChild(BLOCK));
        return module;
    }

    private void body(BslTree parent, AstNode tree) {
        if (tree == null) {
            return;
        }

        List<BslTree> children = parent.getBody();
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        while (cursor.hasNext()) {
            if (cursor.check(COMPOUND_STMT)) {
                compoundStmt(parent, cursor.next());

            }  else if (cursor.check(FUNC_DEF, PROC_DEF)) {
                CallableDefinition callableDefinition = callableDef(cursor.next());
                callableDefinition.setParent(parent);
                children.add(callableDefinition);

            }  else if (cursor.checkThenNext(REGION)) { // ignoring region
                cursor.next(); // skip identifier
                body(parent, cursor.optional(BLOCK));
                cursor.next(); // skip #EndRegion

            } else if (cursor.check(PP_IF)) {
                PreprocessorIf ppIf = new PreprocessorIf(parent, cursor.next().getToken());
                ppIf.setCondition(expression(ppIf, cursor.next()));
                cursor.next(); // skip Then
                body(ppIf, cursor.optional(BLOCK));

                while (cursor.check(PP_ELSIF)) {
                    PreprocessorElsif ppElsIf = new PreprocessorElsif(ppIf, cursor.next().getToken());
                    ppElsIf.setCondition(expression(ppElsIf, cursor.next()));
                    cursor.next(); // skip Then
                    body(ppElsIf, cursor.optional(BLOCK));
                }
                cursor.next(); // skip #EndIf

            }  else if (cursor.check(VAR_DEF)) {
                VariablesDefinition variablesDefinition = variablesDef(cursor.next());
                variablesDefinition.setParent(parent);
                children.add(variablesDefinition);

            } else {
                throw new IllegalStateException("Definition " + cursor.next().getType() + " not correctly translated to strongly typed AST");

            }
        }
    }

    public CallableDefinition callableDef(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        CallableDefinition callable = new CallableDefinition(tree.getToken());
        if (tree.getType() == PROC_DEF) {
            callable.setType(CallableDefinition.Type.PROCEDURE);
        }

        callable.setDirective(compilationDirective(callable, cursor));
        callable.setAsync(cursor.map(n -> new CallableDefinition.Async(callable, n.getToken()), ASYNC));

        cursor.next(); // skip Function / Procedure
        callable.setIdentifier(new Identifier(callable, cursor.next().getToken()));

        cursor.next(); // skip (
        while (cursor.check(PARAMETER)) {
            parameterDef(callable, cursor.next());
            cursor.checkThenNext(COMMA); // skip ,
        }
        cursor.next(); // skip )
        callable.setExport(cursor.map(n -> new Export(callable, n.getToken()), EXPORT));
        body(callable, cursor.optional(BLOCK));

        return callable;
    }

    private void parameterDef(CallableDefinition callable, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Parameter parameter = new Parameter(callable, tree.getToken());
        parameter.setVal(cursor.map(n -> new Parameter.Val(parameter, n.getToken()), VAL));
        parameter.setIdentifier(new Identifier(parameter, cursor.next().getToken()));
        if (cursor.hasNext() && cursor.checkThenNext(EQ)) {
            parameter.setDefaultValue(primitiveExpr(callable, cursor.next()));
        }
    }

    public VariablesDefinition variablesDef(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        VariablesDefinition varDef = new VariablesDefinition(tree.getToken());
        varDef.setDirective(compilationDirective(varDef, cursor));
        cursor.next(); // skip Var
        while (cursor.check(VARIABLE)) {
            variable(varDef, cursor.next());
            cursor.checkThenNext(COMMA); // skip ,
        }
        return varDef;
    }

    private void variable(VariablesDefinition varDef, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Variable variable = new Variable(varDef, tree.getToken());
        variable.setIdentifier(cursor.map(n -> new Identifier(variable, n.getToken())));
        if (cursor.hasNext()) {
            variable.setExport(cursor.map(n -> new Export(variable, n.getToken()), EXPORT));
        }
    }

    private CompilationDirective compilationDirective(BslTree parent, AstSiblingsCursor cursor) {
        return cursor.map(node -> {
            AstSiblingsCursor c = new AstSiblingsCursor(node.getFirstChild());
            c.next(); // skip &
            return new CompilationDirective(parent, node.getToken(), (BslDirective) c.next().getType());
        }, DIRECTIVE);
    }

    public Statement statement(AstNode tree) {
        Function<AstNode, Statement> rule = stmtRules.get(tree.getType());
        if (rule == null) {
            throw new IllegalStateException("Statement " + tree.getType() + " not correctly translated to strongly typed AST");
        }
        return rule.apply(tree);
    }

    public void compoundStmt(BslTree parent, AstNode tree) {
        for (AstNode node : new AstSiblingsCursor(tree.getFirstChild())) {
            if (node.is(SEMICOLON)) {
                continue;
            }
            Statement stmt = statement(node);
            stmt.setParent(parent);
            parent.getBody().add(stmt);
        }
    }

    public AssignmentStatement assignmentStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        AssignmentStatement stmt = new AssignmentStatement(tree.getToken());
        stmt.setTarget(postfixExpression(stmt, cursor.next()));
        cursor.next(); // skip =
        stmt.setExpression(expression(stmt, cursor.next()));
        return stmt;
    }

    public IfStatement ifStatement(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        IfStatement stmt = new IfStatement(tree.getToken());
        cursor.next(); // skip If
        stmt.setCondition(expression(stmt, cursor.next()));
        cursor.next(); // skip Then
        body(stmt, cursor.optional(BLOCK));

        while (cursor.check(ELSIF)) {
            ElsIfBranch elsIfBranch = new ElsIfBranch(stmt, cursor.next().getToken());
            elsIfBranch.setCondition(expression(elsIfBranch, cursor.next()));
            cursor.next(); // skip Then
            body(elsIfBranch, cursor.optional(BLOCK));
        }
        if (cursor.check(ELSE)) {
            ElseClause elseClause = new ElseClause(stmt, cursor.next().getToken());
            body(elseClause, cursor.optional(BLOCK));
        }
        return stmt;
    }

    public WhileStatement whileStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        WhileStatement stmt = new WhileStatement(tree.getToken());
        cursor.next(); // skip While
        stmt.setCondition(expression(stmt, cursor.next()));
        cursor.next(); // skip Do
        body(stmt, cursor.optional(BLOCK));
        return stmt;
    }

    public ForStatement forStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ForStatement stmt = new ForStatement(tree.getToken());
        cursor.next(); // skip For
        stmt.setIdentifier(cursor.map(n -> new Identifier(stmt, n.getToken())));
        cursor.next(); // skip =
        stmt.setInit(expression(stmt, cursor.next()));
        cursor.next(); // skip To
        stmt.setCondition(expression(stmt, cursor.next()));
        cursor.next(); // skip Do
        body(stmt, cursor.optional(BLOCK));
        return stmt;
    }

    public ForEachStatement forEachStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ForEachStatement stmt = new ForEachStatement(tree.getToken());
        cursor.next(); // skip For
        cursor.next(); // skip each
        stmt.setIdentifier(cursor.map(n -> new Identifier(stmt, n.getToken())));
        cursor.next(); // skip In
        stmt.setCollection(expression(stmt, cursor.next()));
        cursor.next(); // skip Do
        body(stmt, cursor.optional(BLOCK));
        return stmt;
    }

    public TryStatement tryStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        TryStatement stmt = new TryStatement(tree.getToken());
        cursor.next(); // skip Try
        body(stmt, cursor.next());

        ExceptClause exceptClause = new ExceptClause(stmt, cursor.next().getToken());
        body(exceptClause, cursor.optional(BLOCK));
        return stmt;
    }

    public CallStatement callStmt(AstNode tree) {
        CallStatement stmt = new CallStatement(tree.getToken());
        stmt.setExpression(postfixExpression(stmt, tree.getFirstChild()));
        return stmt;
    }

    public ReturnStatement returnStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ReturnStatement stmt = new ReturnStatement(tree.getToken());
        cursor.next(); // skip Return
        stmt.setExpression(cursor.map(n -> expression(stmt, n)));
        return stmt;
    }

    public BreakStatement breakStmt(AstNode tree) {
        return new BreakStatement(tree.getToken());
    }

    public ContinueStatement continueStmt(AstNode tree) {
        return new ContinueStatement(tree.getToken());
    }

    public EmptyStatement emptyStmt(AstNode tree) {
        return new EmptyStatement(tree.getToken());
    }

    public GotoStatement gotoStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        cursor.next(); // skip Goto
        GotoStatement stmt = new GotoStatement(tree.getToken());
        stmt.setLabel(label(stmt, cursor.next()));
        return stmt;
    }

    public LabelDefinition labelDef(AstNode tree) {
        LabelDefinition labelDefinition = new LabelDefinition(tree.getToken());
        labelDefinition.setLabel(label(labelDefinition, tree));
        return labelDefinition;
    }

    private Label label(Statement parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Label label = new Label(parent, tree.getFirstChild().getToken());
        cursor.next(); // skip ~
        label.setIdentifier(new Identifier(label, cursor.next().getToken()));
        return label;
    }

    public ExecuteStatement executeStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ExecuteStatement stmt = new ExecuteStatement(tree.getToken());
        cursor.next(); // skip Execute
        cursor.next(); // skip (
        stmt.setExpression(expression(stmt, cursor.next()));
        return stmt;
    }

    public RaiseStatement raiseStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        RaiseStatement stmt = new RaiseStatement(tree.getToken());
        cursor.next(); // skip Raise
        if (cursor.hasNext()) {
            stmt.setExpression(expression(stmt, cursor.next()));
        }
        return stmt;
    }

    public AddHandlerStatement addHandlerStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        AddHandlerStatement stmt = new AddHandlerStatement(tree.getToken());
        cursor.next(); // skip AddHandler
        stmt.setEvent(expression(stmt, cursor.next()));
        cursor.next(); // skip ,
        stmt.setHandler(expression(stmt, cursor.next()));
        return stmt;
    }

    public RemoveHandlerStatement removeHandlerStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        RemoveHandlerStatement stmt = new RemoveHandlerStatement(tree.getToken());
        cursor.next(); // skip RemoveHandler
        stmt.setEvent(expression(stmt, cursor.next()));
        cursor.next(); // skip ,
        stmt.setHandler(expression(stmt, cursor.next()));
        return stmt;
    }

    public Expression expression(AstNode tree) {
        return expression(null, tree);
    }

    public Expression expression(BslTree parent, AstNode tree) {
        if (tree.is(PRIMARY_EXPR, PP_PRIMARY_EXPRESSION)) {
            return primaryExpr(parent, tree);
        }
        if (tree.is(POSTFIX_EXPR, ASSIGNABLE_EXPR, CALLABLE_EXPR)) {
            return postfixExpression(parent, tree);
        }
        if (tree.is(
                OR_EXPR, AND_EXPR,
                RELATIONAL_EXPR, ADDITIVE_EXPR, MULTIPLICATIVE_EXPR,
                PP_AND_EXPRESSION, PP_OR_EXPRESSION)) {
            return binaryExpression(parent, tree);
        }
        if (tree.is(NOT_EXPR, PP_NOT_EXPRESSION, UNARY_EXPR)) {
            return unaryExpression(parent, tree);
        }
        if (tree.is(TERNARY_EXPR)) {
            return ternaryExpression(parent, tree);
        }
        if (tree.is(NEW_EXPR)) {
            return newExpression(parent, tree);
        }

        throw new IllegalStateException("Expression " + tree.getType() + " not correctly translated to strongly typed AST");
    }

    private PostfixExpression postfixExpression(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        PostfixExpression expr = new PostfixExpression(parent, tree.getToken());
        expr.setAwait(cursor.map(n -> new PostfixExpression.Await(expr, n.getToken()), AWAIT));
        expr.setReference(primaryExpr(expr, cursor.next()).as(ReferenceExpression.class));
        expr.setPostfix(postfix(expr, cursor));
        return expr;
    }

    private Postfix postfix(BslTree parent, AstSiblingsCursor outer) {
        if (!outer.hasNext()) {
            return null;
        }

        AstNode node = outer.next();
        AstSiblingsCursor cursor = new AstSiblingsCursor(node.getFirstChild());
        if (node.is(INDEX_POSTFIX, ASSIGNABLE_INDEX_POSTFIX, CALLABLE_INDEX_POSTFIX)) {
            AstSiblingsCursor c = cursor.descend();

            IndexPostfix postfix = new IndexPostfix(parent, node.getToken());
            c.next(); // skip [
            postfix.setIndex(expression(postfix, c.next()));
            postfix.setPostfix(postfix(postfix, cursor));
            return postfix;
        }

        if (node.is(DEREFERENCE_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)) {
            DereferencePostfix postfix = new DereferencePostfix(parent, node.getToken());

            cursor.next(); // skip .
            postfix.setIdentifier(new Identifier(postfix, cursor.next().getToken()));
            postfix.setPostfix(postfix(postfix, cursor));
            return postfix;
        }

        if (node.is(CALL_POSTFIX, ASSIGNABLE_CALL_POSTFIX, CALLABLE_CALL_POSTFIX, CONSTRUCTOR_CALL_POSTFIX)) {
            CallPostfix postfix = new CallPostfix(parent, node.getToken());

            List<Expression> arguments = postfix.getArguments();
            AstSiblingsCursor c = cursor.descend();
            c.next(); // skip (
            boolean addTail = false;
            while (!c.check(RPAREN)) {
                if (c.check(COMMA)) {
                    new CallPostfix.DefaultArgument(postfix, c.next().getToken());
                    addTail = c.check(RPAREN);
                } else {
                    arguments.add(expression(postfix, c.next()));
                    if (c.checkThenNext(COMMA)) {
                        addTail = c.check(RPAREN);
                    }
                }
            }
            if (addTail) {
                new CallPostfix.DefaultArgument(postfix, c.next().getToken());
            }
            postfix.setPostfix(postfix(postfix, cursor));
            return postfix;
        }

        Token token = cursor.next().getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Postfix expected. Got: " + token);
    }

    private BinaryExpression binaryExpression(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        BinaryExpression expr = new BinaryExpression(parent, tree.getToken());
        expr.setLeft(expression(expr, cursor.next()));
        expr.setOperator(binaryOperator(expr, cursor.next()));
        expr.setRight(expression(expr, cursor.next()));
        return expr;
    }

    private Expression unaryExpression(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        UnaryExpression unaryExpression = new UnaryExpression(parent, tree.getToken());
        unaryExpression.setOperator(unaryOperator(unaryExpression, cursor.next()));
        unaryExpression.setExpression(expression(unaryExpression, cursor.next()));

        return unaryExpression;
    }

    private Expression ternaryExpression(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        cursor.next(); // skip ?
        cursor.next(); // skip (

        TernaryExpression ternaryExpression = new TernaryExpression(parent, tree.getToken());

        ternaryExpression.setCondition(expression(ternaryExpression, cursor.next()));

        cursor.next(); // skip ,

        ternaryExpression.setTrueExpression(expression(ternaryExpression, cursor.next()));

        cursor.next(); // skip ,

        ternaryExpression.setFalseExpression(expression(ternaryExpression, cursor.next()));

        return ternaryExpression;
    }

    private Expression newExpression(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        cursor.next(); // skip new

        NewExpression expr = new NewExpression(parent, tree.getToken());
        expr.setObject(primaryExpr(expr, cursor.next()).as(ReferenceExpression.class));
        expr.setPostfix(postfix(expr, cursor));
        return expr;
    }

    private UnaryOperator unaryOperator(UnaryExpression parent, AstNode tree) {
        AstNode node = tree.getFirstChild();

        UnaryOperator operator = new UnaryOperator(parent, tree.getToken());
        if (node.is(MINUS)) {
            operator.setValue(UnaryOperator.Type.MINUS);
        } else if (node.is(NOT)) {
            operator.setValue(UnaryOperator.Type.NOT);
        } else {
            Token token = node.getToken();
            int line = token.getLine();
            throw new RecognitionException(line, "Parse error at line " + line + ": Unary operator expected. Got: " + token);
        }
        return operator;
    }

    private BinaryOperator binaryOperator(BinaryExpression parent, AstNode tree) {
        AstNode node = tree.getFirstChild();

        BinaryOperator operator = new BinaryOperator(parent, tree.getToken());
        if (node.is(EQ)) {
            operator.setValue(BinaryOperator.Type.EQ);
        } else if (node.is(OR)) {
            operator.setValue(BinaryOperator.Type.OR);
        } else if (node.is(AND)) {
            operator.setValue(BinaryOperator.Type.AND);
        } else if (node.is(PLUS)) {
            operator.setValue(BinaryOperator.Type.ADD);
        } else if (node.is(MINUS)) {
            operator.setValue(BinaryOperator.Type.SUB);
        } else if (node.is(MUL)) {
            operator.setValue(BinaryOperator.Type.MUL);
        } else if (node.is(DIV)) {
            operator.setValue(BinaryOperator.Type.DIV);
        } else if (node.is(MOD)) {
            operator.setValue(BinaryOperator.Type.MOD);
        } else if (node.is(GT)) {
            operator.setValue(BinaryOperator.Type.GT);
        } else if (node.is(GE)) {
            operator.setValue(BinaryOperator.Type.GE);
        } else if (node.is(LT)) {
            operator.setValue(BinaryOperator.Type.LT);
        } else if (node.is(LE)) {
            operator.setValue(BinaryOperator.Type.LE);
        } else if (node.is(NEQ)) {
            operator.setValue(BinaryOperator.Type.NEQ);
        } else {
            Token token = node.getToken();
            int line = token.getLine();
            throw new RecognitionException(line, "Parse error at line " + line + ": Unknown binary operator: " + token);
        }
        return operator;
    }

    private Expression primaryExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        if (cursor.check(IDENTIFIER)) {
            ReferenceExpression expr = new ReferenceExpression(parent, tree.getToken());
            expr.setIdentifier(new Identifier(expr, cursor.next().getToken()));
            return expr;
        }

        if (cursor.check(PRIMITIVE)) {
            return primitiveExpr(parent, cursor.next());
        }

        if (cursor.check(LPAREN)) {
            ParenthesisExpression expr = new ParenthesisExpression(parent, cursor.next().getToken());
            expr.setExpression(expression(expr, cursor.next()));
            return expr;
        }

        Token token = cursor.next().getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Identifier, primitive or parenthesis expression expected. Got " + token);
    }

    private PrimitiveExpression primitiveExpr(BslTree parent, AstNode tree) {
        AstNode node = tree.getFirstChild();
        if (node.is(STRING)) {
            PrimitiveString primitiveString = new PrimitiveString(parent, node.getToken());
            List<BslTree> body = primitiveString.getBody();
            for (AstNode n : new AstSiblingsCursor(node.getFirstChild())) {
                body.add(primitiveStr(primitiveString, n));
            }
            return primitiveString;
        }
        if (node.is(NUMBER)) {
            return new PrimitiveNumber(parent, node.getToken());
        }
        if (node.is(TRUE)) {
            return new PrimitiveTrue(parent, node.getToken());
        }
        if (node.is(FALSE)) {
            return new PrimitiveFalse(parent, node.getToken());
        }
        if (node.is(UNDEFINED)) {
            return new PrimitiveUndefined(parent, node.getToken());
        }
        if (node.is(DATE)) {
            return new PrimitiveDate(parent, node.getToken());
        }
        if (node.is(NULL)) {
            return new PrimitiveNull(parent, node.getToken());
        }

        Token token = node.getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Unknown literal token: " + token);
    }

    private StringLiteral primitiveStr(PrimitiveString parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        cursor.next(); // skip "
        StringBuilder builder = new StringBuilder();
        while (!cursor.check(QUOTE)) {
            if (cursor.check(STRING_CONTENT)) {
                builder.append(cursor.next().getTokenOriginalValue());
            } else if (cursor.checkThenNext(PIPE)) {
                builder.append('\n');
            } else {
                Token token = cursor.next().getToken();
                int line = token.getLine();
                throw new RecognitionException(line, "Parse error at line " + line + ": Unknown string literal token: " + token);
            }
        }
        return new StringLiteral(parent, tree.getToken(), builder.toString());
    }
}
