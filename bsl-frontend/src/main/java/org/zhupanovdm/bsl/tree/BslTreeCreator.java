package org.zhupanovdm.bsl.tree;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.Token;
import org.zhupanovdm.bsl.grammar.BslDirective;
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

import static org.zhupanovdm.bsl.grammar.BslGrammar.*;
import static org.zhupanovdm.bsl.grammar.BslKeyword.*;
import static org.zhupanovdm.bsl.grammar.BslPunctuator.*;

public class BslTreeCreator {
    private final Map<AstNodeType, Function<AstNode, BslTree>> stmtRules = new HashMap<>();
    private final Map<AstNodeType, BslTree.Type> binOpRules = new HashMap<>();

    public BslTreeCreator() {
        stmtRules.put(ASSIGN_STMT,      this::assignStmt);
        stmtRules.put(IF_STMT,          this::ifStmt);
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

        binOpRules.put(PLUS,            BslTree.Type.ADD);
        binOpRules.put(MINUS,           BslTree.Type.SUB);
        binOpRules.put(STAR,            BslTree.Type.MUL);
        binOpRules.put(SLASH,           BslTree.Type.DIV);
        binOpRules.put(PERCENT,         BslTree.Type.MOD);

        binOpRules.put(AND,             BslTree.Type.AND);
        binOpRules.put(OR,              BslTree.Type.OR);
        binOpRules.put(NOT,             BslTree.Type.NOT);

        binOpRules.put(EQ,              BslTree.Type.EQ);
        binOpRules.put(NEQ,             BslTree.Type.NEQ);
        binOpRules.put(GT,              BslTree.Type.GT);
        binOpRules.put(GE,              BslTree.Type.GE);
        binOpRules.put(LT,              BslTree.Type.LT);
        binOpRules.put(LE,              BslTree.Type.LE);
    }

    public Module create(AstNode tree) {
        if (!tree.is(MODULE)) {
            throw new IllegalArgumentException("Is not BSL module AST");
        }
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        Module module = new Module();
        body(module, cursor.optional(BODY));
        module.addToken(cursor.next().getToken());
        return module;
    }

    private void body(BslTree parent, AstNode tree) {
        if (tree == null) {
            return;
        }

        List<BslTree> body = parent.getBody();
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        while (cursor.hasNext()) {
            if (cursor.has(COMPOUND_STMT)) {
                compoundStmt(parent, cursor.next());

            }  else if (cursor.has(FUNC_DEF)) {
                CallableDefinition definition = callableDef(cursor.next(), BslTree.Type.FUNCTION);
                definition.setParent(parent);
                body.add(definition);

            }  else if (cursor.has(PROC_DEF)) {
                CallableDefinition definition = callableDef(cursor.next(), BslTree.Type.PROCEDURE);
                definition.setParent(parent);
                body.add(definition);

            }  else if (cursor.has(REGION)) {
                addTokens(parent, cursor.next()); // #Region
                parent.addToken(cursor.next().getToken()); // name
                body(parent, cursor.optional(BODY));
                addTokens(parent, cursor.next()); // #EndRegion

            } else if (cursor.has(PP_IF)) {
                PreprocessorIf ppIf = new PreprocessorIf(parent);
                addTokens(ppIf, cursor.next()); // #If
                ppIf.setCondition(expression(ppIf, cursor.next()));
                ppIf.addToken(cursor.next().getToken()); // Then
                body(ppIf, cursor.optional(BODY));

                while (cursor.has(PP_ELSIF)) {
                    PreprocessorElsif ppElsIf = new PreprocessorElsif(ppIf);
                    addTokens(ppElsIf, cursor.next()); // #ElsIf
                    ppElsIf.setCondition(expression(ppElsIf, cursor.next()));
                    ppElsIf.addToken(cursor.next().getToken()); // Then
                    body(ppElsIf, cursor.optional(BODY));

                    ppIf.getElsIfBranches().add(ppElsIf);
                }
                addTokens(ppIf, cursor.next()); // #EndIf

                body.add(ppIf);

            }  else if (cursor.has(VAR_DEF)) {
                VariablesDefinition definition = variablesDef(cursor.next());
                definition.setParent(parent);

                body.add(definition);

            } else {
                throw new IllegalStateException("Definition " + cursor.next().getType() + " not correctly translated to strongly typed AST");

            }
        }
    }

    public CallableDefinition callableDef(AstNode tree, BslTree.Type type) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        CallableDefinition callable = new CallableDefinition(type);

        if (cursor.has(DIRECTIVE)) {
            callable.setDirective(directive(callable, cursor.next()));
        }
        if (cursor.has(ASYNC)) {
            callable.setAsync(true);
            callable.addToken(cursor.next().getToken());
        }

        callable.addToken(cursor.next().getToken()); // Function / Procedure

        AstNode name = cursor.next().getFirstChild();
        callable.setName(name.getTokenOriginalValue());
        callable.addToken(name.getToken());

        callable.addToken(cursor.next().getToken()); // (
        List<Parameter> params = callable.getParameters();
        while (cursor.has(PARAMETER)) {
            params.add(parameter(callable, cursor.next()));
            if (cursor.has(COMMA)) {
                callable.addToken(cursor.next().getToken()); // ,
            }
        }
        callable.addToken(cursor.next().getToken()); // )

        if (cursor.has(EXPORT)) {
            callable.setExport(true);
            callable.addToken(cursor.next().getToken());
        }

        body(callable, cursor.optional(BODY));
        callable.addToken(cursor.next().getToken()); // EndFunction / EndProcedure

        return callable;
    }

    private Parameter parameter(CallableDefinition callable, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Parameter parameter = new Parameter(callable);
        if (cursor.has(VAL)) {
            parameter.setVal(true);
            parameter.addToken(cursor.next().getToken());
        }

        AstNode name = cursor.next().getFirstChild();
        parameter.setName(name.getTokenOriginalValue());
        parameter.addToken(name.getToken());

        if (cursor.hasNext()) {
            parameter.addToken(cursor.next().getToken()); // =
            parameter.setDefaultValue(primitiveExpr(parameter, cursor.next()));
        }

        return parameter;
    }

    public VariablesDefinition variablesDef(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        VariablesDefinition varDef = new VariablesDefinition();
        if (cursor.has(DIRECTIVE)) {
            varDef.setDirective(directive(varDef, cursor.next()));
        }

        varDef.addToken(cursor.next().getToken()); // Var
        List<BslTree> variables = varDef.getBody();
        while (cursor.has(VARIABLE)) {
            variables.add(variable(varDef, cursor.next()));
            if (cursor.has(COMMA)) {
                varDef.addToken(cursor.next().getToken()); // ,
            }
        }
        varDef.addToken(cursor.next().getToken()); // ;

        return varDef;
    }

    private Variable variable(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Variable variable = new Variable(parent);

        AstNode name = cursor.next().getFirstChild();
        variable.setName(name.getTokenOriginalValue());
        variable.addToken(name.getToken());

        if (cursor.hasNext()) {
            variable.setExport(true);
            variable.addToken(cursor.next().getToken());
        }

        return variable;
    }

    private Directive directive(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Directive directive = new Directive(parent);
        directive.addToken(cursor.next().getToken()); // &

        AstNode node = cursor.next();
        directive.setValue((BslDirective) node.getType());
        directive.addToken(node.getToken());

        return directive;
    }

    public BslTree statement(AstNode tree) {
        Function<AstNode, BslTree> rule = stmtRules.get(tree.getType());
        if (rule == null) {
            throw new IllegalStateException("Statement " + tree.getType() + " not correctly translated to strongly typed AST");
        }
        return rule.apply(tree);
    }

    public void compoundStmt(BslTree parent, AstNode tree) {
        BslTree stmt = null;
        for (AstNode node : new AstSiblingsCursor(tree.getFirstChild())) {
            if (node.is(SEMICOLON)) {
                if (stmt == null) {
                    Token token = node.getToken();
                    int line = token.getLine();
                    throw new RecognitionException(line, "Parse error at line " + line + ": Statements delimiter is not expected.");
                }
                stmt.addToken(node.getToken());
            } else {
                stmt = statement(node);
                stmt.setParent(parent);
                parent.getBody().add(stmt);
            }
        }
    }

    public AssignmentStatement assignStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        AssignmentStatement stmt = new AssignmentStatement();
        stmt.setTarget(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // =
        stmt.setExpression(expression(stmt, cursor.next()));

        return stmt;
    }

    public IfStatement ifStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        IfStatement stmt = new IfStatement();
        stmt.addToken(cursor.next().getToken()); // If
        stmt.setCondition(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // Then
        body(stmt, cursor.optional(BODY));

        List<ElsIfBranch> branches = stmt.getElsIfBranches();
        while (cursor.has(ELSIF)) {
            ElsIfBranch elsIfBranch = new ElsIfBranch(stmt);
            elsIfBranch.addToken(cursor.next().getToken()); // ElsIf
            elsIfBranch.setCondition(expression(elsIfBranch, cursor.next()));
            elsIfBranch.addToken(cursor.next().getToken()); // Then
            body(elsIfBranch, cursor.optional(BODY));

            branches.add(elsIfBranch);
        }

        if (cursor.has(ELSE)) {
            ElseClause elseClause = new ElseClause(stmt);
            elseClause.addToken(cursor.next().getToken()); // Else
            body(elseClause, cursor.optional(BODY));

            stmt.setElseClause(elseClause);
        }

        stmt.addToken(cursor.next().getToken()); // EndIf

        return stmt;
    }

    public WhileStatement whileStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        WhileStatement stmt = new WhileStatement();
        stmt.addToken(cursor.next().getToken()); // While
        stmt.setCondition(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // Do
        body(stmt, cursor.optional(BODY));
        stmt.addToken(cursor.next().getToken()); // EndDo

        return stmt;
    }

    public ForStatement forStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ForStatement stmt = new ForStatement();
        stmt.addToken(cursor.next().getToken()); // For
        stmt.setVariable(variable(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // =
        stmt.setInit(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // To
        stmt.setCondition(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // Do
        body(stmt, cursor.optional(BODY));
        stmt.addToken(cursor.next().getToken()); // EndDo

        return stmt;
    }

    public ForEachStatement forEachStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ForEachStatement stmt = new ForEachStatement();
        stmt.addToken(cursor.next().getToken()); // For
        stmt.addToken(cursor.next().getToken()); // each
        stmt.setVariable(variable(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // In
        stmt.setCollection(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // Do
        body(stmt, cursor.optional(BODY));
        stmt.addToken(cursor.next().getToken()); // EndDo

        return stmt;
    }

    public TryStatement tryStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        TryStatement stmt = new TryStatement();
        stmt.addToken(cursor.next().getToken()); // Try
        body(stmt, cursor.next());
        stmt.addToken(cursor.next().getToken()); // Except

        ExceptClause exceptClause = new ExceptClause(stmt);
        stmt.setExceptClause(exceptClause);
        body(exceptClause, cursor.optional(BODY));
        stmt.addToken(cursor.next().getToken()); // EndTry

        return stmt;
    }

    public CallStatement callStmt(AstNode tree) {
        CallStatement stmt = new CallStatement();
        stmt.setExpression(postfixExpr(stmt, tree.getFirstChild()));
        return stmt;
    }

    public ReturnStatement returnStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ReturnStatement stmt = new ReturnStatement();
        stmt.addToken(cursor.next().getToken()); // Return
        if (cursor.hasNext()) {
            stmt.setExpression(expression(stmt, cursor.next()));
        }

        return stmt;
    }

    public BreakStatement breakStmt(AstNode tree) {
        BreakStatement stmt = new BreakStatement();
        stmt.addToken(tree.getFirstChild().getToken());
        return stmt;
    }

    public ContinueStatement continueStmt(AstNode tree) {
        ContinueStatement stmt = new ContinueStatement();
        stmt.addToken(tree.getFirstChild().getToken());
        return stmt;
    }

    public EmptyStatement emptyStmt(AstNode tree) {
        EmptyStatement stmt = new EmptyStatement();
        stmt.addToken(tree.getFirstChild().getToken());
        return stmt;
    }

    public GotoStatement gotoStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        GotoStatement stmt = new GotoStatement();
        stmt.addToken(cursor.next().getToken()); // Goto
        stmt.setLabel(label(stmt, cursor.next()));

        return stmt;
    }

    public LabelDefinition labelDef(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        LabelDefinition def = new LabelDefinition();
        def.setLabel(label(def, cursor.next()));
        def.addToken(cursor.next().getToken()); // :

        return def;
    }

    private Label label(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        Label label = new Label(parent);
        label.addToken(cursor.next().getToken()); // ~

        AstNode name = cursor.next();
        label.setName(name.getTokenOriginalValue());
        label.addToken(name.getToken());

        return label;
    }

    public ExecuteStatement executeStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        ExecuteStatement stmt = new ExecuteStatement();
        stmt.addToken(cursor.next().getToken()); // Execute
        stmt.addToken(cursor.next().getToken()); // (
        stmt.setExpression(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // )

        return stmt;
    }

    public RaiseStatement raiseStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        RaiseStatement stmt = new RaiseStatement();
        stmt.addToken(cursor.next().getToken()); // Raise
        if (cursor.hasNext()) {
            stmt.setExpression(expression(stmt, cursor.next()));
        }

        return stmt;
    }

    public AddHandlerStatement addHandlerStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        AddHandlerStatement stmt = new AddHandlerStatement();
        stmt.addToken(cursor.next().getToken()); // AddHandler
        stmt.setEvent(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // ,
        stmt.setHandler(expression(stmt, cursor.next()));

        return stmt;
    }

    public RemoveHandlerStatement removeHandlerStmt(AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        RemoveHandlerStatement stmt = new RemoveHandlerStatement();
        stmt.addToken(cursor.next().getToken()); // RemoveHandler
        stmt.setEvent(expression(stmt, cursor.next()));
        stmt.addToken(cursor.next().getToken()); // ,
        stmt.setHandler(expression(stmt, cursor.next()));

        return stmt;
    }

    public BslTree expression(AstNode tree) {
        return expression(null, tree);
    }

    public BslTree expression(BslTree parent, AstNode tree) {
        if (tree.is(PRIMARY_EXPR, PP_PRIMARY_EXPRESSION)) {
            return primaryExpr(parent, tree);
        }
        if (tree.is(POSTFIX_EXPR, CALLABLE_EXPR)) {
            return postfixExpr(parent, tree);
        }
        if (tree.is(ASSIGNABLE_EXPR)) {
            if (tree.getFirstChild().is(VARIABLE_LOCAL)) {
                return variable(parent, tree.getFirstChild());
            }
            return postfixExpr(parent, tree);
        }
        if (tree.is(
                OR_EXPR, AND_EXPR,
                RELATIONAL_EXPR, ADDITIVE_EXPR, MULTIPLICATIVE_EXPR,
                PP_AND_EXPRESSION, PP_OR_EXPRESSION)) {
            return binaryExpr(parent, tree);
        }
        if (tree.is(NOT_EXPR, PP_NOT_EXPRESSION, UNARY_EXPR)) {
            return unaryExpr(parent, tree);
        }
        if (tree.is(TERNARY_EXPR)) {
            return ternaryExpr(parent, tree);
        }
        if (tree.is(NEW_EXPR)) {
            return newExpr(parent, tree);
        }

        throw new IllegalStateException("Expression " + tree.getType() + " not correctly translated to strongly typed AST");
    }

    private PostfixExpression postfixExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        PostfixExpression expr = new PostfixExpression(parent);
        if (cursor.has(AWAIT)) {
            expr.setAwait(true);
            expr.addToken(cursor.next().getToken());
        }
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

            IndexPostfix postfix = new IndexPostfix(parent);
            postfix.addToken(c.next().getToken()); // [
            postfix.setIndex(expression(postfix, c.next()));
            postfix.addToken(c.next().getToken()); // ]
            postfix.setPostfix(postfix(postfix, cursor));

            return postfix;
        }

        if (node.is(DEREFERENCE_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)) {
            DereferencePostfix postfix = new DereferencePostfix(parent);
            postfix.addToken(cursor.next().getToken()); // .

            AstNode name = cursor.next().getFirstChild();
            postfix.setName(name.getTokenOriginalValue());
            postfix.addToken(name.getToken());

            postfix.setPostfix(postfix(postfix, cursor));

            return postfix;
        }

        if (node.is(CALL_POSTFIX, ASSIGNABLE_CALL_POSTFIX, CALLABLE_CALL_POSTFIX, CONSTRUCTOR_CALL_POSTFIX)) {
            AstSiblingsCursor c = cursor.descend();

            CallPostfix postfix = new CallPostfix(parent);
            postfix.addToken(c.next().getToken()); // (

            boolean addTail = false;
            List<BslTree> arguments = postfix.getArguments();
            while (!c.has(RPAREN)) {
                if (c.has(COMMA)) {
                    postfix.addToken(c.next().getToken());
                    arguments.add(new EmptyExpression(postfix));
                    addTail = c.has(RPAREN);
                } else {
                    arguments.add(expression(postfix, c.next()));
                    if (c.has(COMMA)) {
                        postfix.addToken(c.next().getToken());
                        addTail = c.has(RPAREN);
                    }
                }
            }
            postfix.addToken(c.next().getToken()); // )
            if (addTail) {
                arguments.add(new EmptyExpression(postfix));
            }

            postfix.setPostfix(postfix(postfix, cursor));

            return postfix;
        }

        Token token = cursor.next().getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Postfix expected. Got: " + token);
    }

    private BinaryExpression binaryExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        BinaryExpression expr = new BinaryExpression(parent);
        expr.setLeft(expression(expr, cursor.next()));

        AstNode node = cursor.next().getFirstChild();
        BslTree.Type op = binOpRules.get(node.getType());
        if (op == null) {
            Token token = node.getToken();
            int line = token.getLine();
            throw new RecognitionException(line, "Parse error at line " + line + ": Unknown binary operator: " + token);
        }
        expr.setType(op);
        expr.addToken(node.getToken());

        expr.setRight(expression(expr, cursor.next()));

        return expr;
    }

    private UnaryExpression unaryExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        UnaryExpression expr = new UnaryExpression(parent);

        AstNode node = cursor.next().getFirstChild();
        if (node.is(MINUS)) {
            expr.setType(BslTree.Type.MINUS);
        } else if (node.is(NOT)) {
            expr.setType(BslTree.Type.NOT);
        } else {
            Token token = node.getToken();
            int line = token.getLine();
            throw new RecognitionException(line, "Parse error at line " + line + ": Unary operator expected. Got: " + token);
        }
        expr.addToken(node.getToken());

        expr.setExpression(expression(expr, cursor.next()));

        return expr;
    }

    private TernaryExpression ternaryExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        TernaryExpression expr = new TernaryExpression(parent);
        expr.addToken(cursor.next().getToken()); // ?
        expr.addToken(cursor.next().getToken()); // (
        expr.setCondition(expression(expr, cursor.next()));
        expr.addToken(cursor.next().getToken()); // ,
        expr.setTrueExpression(expression(expr, cursor.next()));
        expr.addToken(cursor.next().getToken()); // ,
        expr.setFalseExpression(expression(expr, cursor.next()));
        expr.addToken(cursor.next().getToken()); // )

        return expr;
    }

    private BslTree newExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        NewExpression expr = new NewExpression(parent);
        expr.addToken(cursor.next().getToken()); // new

        AstNode name = cursor.next().getFirstChild();
        expr.setName(name.getTokenOriginalValue());
        expr.addToken(name.getToken());

        expr.setPostfix(postfix(expr, cursor));

        return expr;
    }

    private BslTree primaryExpr(BslTree parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());

        if (cursor.has(IDENTIFIER)) {
            ReferenceExpression expr = new ReferenceExpression(parent);

            AstNode name = cursor.next().getFirstChild();
            expr.setName(name.getTokenOriginalValue());
            expr.addToken(name.getToken());

            return expr;
        }

        if (cursor.has(PRIMITIVE)) {
            return primitiveExpr(parent, cursor.next());
        }

        if (cursor.has(LPAREN)) {
            ParenthesisExpression expr = new ParenthesisExpression(parent);
            expr.addToken(cursor.next().getToken()); // (
            expr.setExpression(expression(expr, cursor.next()));
            expr.addToken(cursor.next().getToken()); // )

            return expr;
        }

        Token token = cursor.next().getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Identifier, primitive or parenthesis expression expected. Got " + token);
    }

    private PrimitiveExpression primitiveExpr(BslTree parent, AstNode tree) {
        AstNode node = tree.getFirstChild();
        if (node.is(STRING)) {
            StringBuilder builder = new StringBuilder();
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.STRING);
            List<BslTree> body = expr.getBody();
            for (AstNode n : new AstSiblingsCursor(node.getFirstChild())) {
                PrimitiveExpression literal = stringLiteral(expr, n);
                builder.append(literal.getValue());
                body.add(literal);
            }
            expr.setValue(builder.toString());
            return expr;
        }
        if (node.is(NUMBER)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.NUMBER);
            expr.addToken(node.getToken());
            expr.setValue(node.getTokenOriginalValue());
            return expr;
        }
        if (node.is(TRUE)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.TRUE);
            expr.addToken(node.getToken());
            expr.setValue(node.getTokenOriginalValue());
            return expr;
        }
        if (node.is(FALSE)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.FALSE);
            expr.addToken(node.getToken());
            expr.setValue(node.getTokenOriginalValue());
            return expr;
        }
        if (node.is(UNDEFINED)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.UNDEFINED);
            expr.addToken(node.getToken());
            expr.setValue(node.getTokenOriginalValue());
            return expr;
        }
        if (node.is(DATE)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.DATE);
            expr.addToken(node.getToken());

            String value = node.getTokenOriginalValue();
            expr.setValue(value.substring(1).substring(0, value.length() - 2));

            return expr;
        }
        if (node.is(NULL)) {
            PrimitiveExpression expr = new PrimitiveExpression(parent, BslTree.Type.NULL);
            expr.addToken(node.getToken());
            expr.setValue(node.getTokenOriginalValue());
            return expr;
        }

        Token token = node.getToken();
        int line = token.getLine();
        throw new RecognitionException(line, "Parse error at line " + line + ": Unknown literal token: " + token);
    }

    private PrimitiveExpression stringLiteral(PrimitiveExpression parent, AstNode tree) {
        AstSiblingsCursor cursor = new AstSiblingsCursor(tree.getFirstChild());
        StringBuilder builder = new StringBuilder();

        PrimitiveExpression literal = new PrimitiveExpression(parent, BslTree.Type.STRING);
        literal.addToken(cursor.next().getToken()); // "
        while (!cursor.has(QUOTE)) {
            if (cursor.has(STRING_CONTENT)) {
                AstNode node = cursor.next();
                literal.addToken(node.getToken());
                builder.append(node.getTokenOriginalValue());
            } else if (cursor.has(PIPE)) {
                builder.append('\n');
                literal.addToken(cursor.next().getToken());
            } else {
                Token token = cursor.next().getToken();
                int line = token.getLine();
                throw new RecognitionException(line, "Parse error at line " + line + ": Unknown string literal token: " + token);
            }
        }
        literal.addToken(cursor.next().getToken()); // "

        literal.setValue(builder.toString());

        return literal;
    }

    private static void addTokens(BslTree target, AstNode tree) {
        for (AstNode n : new AstSiblingsCursor(tree.getFirstChild())) {
            target.addToken(n.getToken());
        }
    }

    public static Module module(AstNode tree) {
        return new BslTreeCreator().create(tree);
    }
}
