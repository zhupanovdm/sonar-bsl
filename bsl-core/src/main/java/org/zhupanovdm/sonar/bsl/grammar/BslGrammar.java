package org.zhupanovdm.sonar.bsl.grammar;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.zhupanovdm.sonar.utils.BilingualUtils;

import static org.zhupanovdm.sonar.bsl.grammar.BslCompilationDirective.*;
import static org.zhupanovdm.sonar.bsl.grammar.BslKeyword.*;
import static org.zhupanovdm.sonar.bsl.grammar.BslPunctuator.*;

public enum BslGrammar implements GrammarRuleKey {
    WHITESPACE,
    SPACING,
    KEYWORDS,

    MODULE,

    VAR_DEFINITION,
    VARIABLE,

    FUNC_DEFINITION,
    PROC_DEFINITION,

    CALLABLE_DEFINITION,
    CALLABLE_SIGNATURE,
    CALLABLE_PARAMETERS,
    PARAMETER,
    PARAMETER_DEFAULT_VALUE,

    LABEL_DEFINITION,

    STATEMENT,
    COMPOUND_STATEMENT,
    ASSIGNMENT_STATEMENT,
    CALL_STATEMENT,
    IF_STATEMENT,
    WHILE_STATEMENT,
    FOR_STATEMENT,
    FOREACH_STATEMENT,
    RAISE_STATEMENT,
    EXECUTE_STATEMENT,
    ADD_HANDLER_STATEMENT,
    CONTINUE_STATEMENT,
    BREAK_STATEMENT,
    RETURN_STATEMENT,
    GOTO_STATEMENT,
    TRY_STATEMENT,
    REMOVE_HANDLER_STATEMENT,
    EMPTY_STATEMENT,

    ASSIGNABLE,
    ASSIGNABLE_DEREFERENCE_POSTFIX,
    ASSIGNABLE_CALL_POSTFIX,
    ASSIGNABLE_INDEX_POSTFIX,

    CALLABLE,
    CALLABLE_DEREFERENCE_POSTFIX,
    CALLABLE_CALL_POSTFIX,
    CALLABLE_INDEX_POSTFIX,

    EXPRESSION,
    LOGIC_OR_EXPRESSION,
    LOGIC_AND_EXPRESSION,
    LOGIC_NOT_EXPRESSION,
    RELATIONAL_EXPRESSION,
    ADDITIVE_EXPRESSION,
    MULTIPLICATIVE_EXPRESSION,
    UNARY_EXPRESSION,
    POSTFIX_EXPRESSION,
    INDEX_POSTFIX,
    DEREFERENCE_POSTFIX,
    CALL_POSTFIX,
    TERNARY_EXPRESSION,
    PRIMARY_EXPRESSION,
    NEW_EXPRESSION,

    LOGIC_OPERATOR,
    RELATIONAL_OPERATOR,
    ADDITIVE_OPERATOR,
    MULTIPLICATIVE_OPERATOR,
    UNARY_OPERATOR,
    INDEX_OPERATOR,
    CALL_OPERATOR,

    IDENTIFIER,
    LABEL,
    IDENTIFIER_PART,

    PRIMITIVE,
    STRING,
    STRING_PART,
    NUMBER,
    DATE,

    VAR_DIRECTIVE,
    CALLABLE_DIRECTIVE;

    private static final String UNICODE_LETTER = "\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}";
    private static final String UNICODE_DIGIT = "\\p{Nd}";
    private static final String UNICODE_COMBINING_MARK = "\\p{Mn}\\p{Mc}";
    private static final String UNICODE_CONNECTOR_PUNCTUATION = "\\p{Pc}";

    private static final String IDENTIFIER_START_REGEXP = "(?:[_" + UNICODE_LETTER + "])";
    private static final String IDENTIFIER_PART_REGEXP = "(?:" + IDENTIFIER_START_REGEXP +
            "|[" + UNICODE_COMBINING_MARK + UNICODE_DIGIT + UNICODE_CONNECTOR_PUNCTUATION + "])";

    private static final String STRING_REGEXP = "\\\"(?:[^\\\"\\r\\n]|\\\"{2}|(?:[\\r\\n]\\|))*\\\"";
    private static final String NUMBER_REGEXP = "[0-9]+(?:\\.(?:[0-9]++)?+)?";
    private static final String DATE_REGEXP = "'(?:\\d{8}(?:\\d{6})?|\\d{4}\\.\\d{2}\\.\\d{2}(?: \\d{2}:\\d{2}:\\d{2})?)'";

    public static LexerlessGrammar createGrammar() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        b.rule(WHITESPACE).is(b.regexp("[\\n\\r\\p{Zl}\\p{Zp}\\t\\v\\f\\u0020\\u00A0\\uFEFF\\p{Zs}]*+"));

        b.rule(SPACING).is(
            b.skippedTrivia(WHITESPACE),
            b.zeroOrMore(b.commentTrivia(b.regexp("//[^\\n\\r]*+")), b.skippedTrivia(WHITESPACE))
        ).skip();

        b.rule(IDENTIFIER_PART).is(b.regexp(IDENTIFIER_PART_REGEXP));

        b.rule(MODULE).is(
            b.zeroOrMore(VAR_DEFINITION),
            b.zeroOrMore(b.firstOf(FUNC_DEFINITION, PROC_DEFINITION)),
            b.optional(COMPOUND_STATEMENT),
            SPACING,
            b.endOfInput());

        punctuators(b);
        keywords(b);
        literals(b);
        definitions(b);
        expressions(b);
        statements(b);
        directives(b);

        b.setRootRule(MODULE);

        return b.build();
    }

    private static void literals(LexerlessGrammarBuilder b) {
        b.rule(STRING).is(STRING_PART, b.zeroOrMore(STRING_PART));
        b.rule(STRING_PART).is(SPACING, b.regexp(STRING_REGEXP));

        b.rule(NUMBER).is(SPACING, b.regexp(NUMBER_REGEXP));

        b.rule(DATE).is(SPACING, b.regexp(DATE_REGEXP));
    }

    private static void expressions(LexerlessGrammarBuilder b) {
        b.rule(EXPRESSION).is(LOGIC_OR_EXPRESSION);

        b.rule(LOGIC_OR_EXPRESSION).is(LOGIC_AND_EXPRESSION, b.optional(b.sequence(b.next(OR), LOGIC_OPERATOR), LOGIC_OR_EXPRESSION)).skipIfOneChild();
        b.rule(LOGIC_AND_EXPRESSION).is(LOGIC_NOT_EXPRESSION, b.optional(b.sequence(b.next(AND), LOGIC_OPERATOR), LOGIC_AND_EXPRESSION)).skipIfOneChild();
        b.rule(LOGIC_NOT_EXPRESSION).is(b.firstOf(b.sequence(b.next(NOT), LOGIC_OPERATOR, POSTFIX_EXPRESSION), RELATIONAL_EXPRESSION)).skipIfOneChild();
        b.rule(LOGIC_OPERATOR).is(b.firstOf(OR, AND, NOT));

        b.rule(RELATIONAL_EXPRESSION).is(ADDITIVE_EXPRESSION, b.optional(RELATIONAL_OPERATOR, RELATIONAL_EXPRESSION)).skipIfOneChild();
        b.rule(RELATIONAL_OPERATOR).is(b.firstOf(NEQ, LE, GE, GT, LT, EQ));

        b.rule(ADDITIVE_EXPRESSION).is(MULTIPLICATIVE_EXPRESSION, b.optional(ADDITIVE_OPERATOR, ADDITIVE_EXPRESSION)).skipIfOneChild();
        b.rule(ADDITIVE_OPERATOR).is(b.firstOf(PLUS, MINUS));

        b.rule(MULTIPLICATIVE_EXPRESSION).is(UNARY_EXPRESSION, b.optional(MULTIPLICATIVE_OPERATOR, MULTIPLICATIVE_EXPRESSION)).skipIfOneChild();
        b.rule(MULTIPLICATIVE_OPERATOR).is(b.firstOf(MUL, DIV, MOD));

        b.rule(UNARY_EXPRESSION).is(b.firstOf(b.sequence(UNARY_OPERATOR, POSTFIX_EXPRESSION), POSTFIX_EXPRESSION)).skipIfOneChild();
        b.rule(UNARY_OPERATOR).is(b.firstOf(PLUS, MINUS));

        b.rule(POSTFIX_EXPRESSION).is(b.firstOf(
            b.sequence(b.sequence(b.next(IDENTIFIER), PRIMARY_EXPRESSION), b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX, CALL_POSTFIX)),
            PRIMARY_EXPRESSION,
            NEW_EXPRESSION,
            TERNARY_EXPRESSION)
        ).skipIfOneChild();
        b.rule(INDEX_POSTFIX).is(INDEX_OPERATOR, b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX)));
        b.rule(DEREFERENCE_POSTFIX).is(
            DOT, IDENTIFIER,
            b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX, CALL_POSTFIX)));
        b.rule(CALL_POSTFIX).is(CALL_OPERATOR, b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX)));

        b.rule(INDEX_OPERATOR).is(LBRACK, EXPRESSION, RBRACK);
        b.rule(CALL_OPERATOR).is(LPAREN, b.optional(EXPRESSION), b.zeroOrMore(COMMA, b.optional(EXPRESSION)), RPAREN);

        b.rule(TERNARY_EXPRESSION).is(
            QUESTION, LPAREN,
            EXPRESSION, COMMA,
            EXPRESSION, COMMA,
            EXPRESSION, RPAREN);

        b.rule(NEW_EXPRESSION).is(NEW, IDENTIFIER, b.optional(CALL_OPERATOR));

        b.rule(PRIMARY_EXPRESSION).is(b.firstOf(IDENTIFIER, PRIMITIVE, b.sequence(LPAREN, EXPRESSION, RPAREN)));

        b.rule(IDENTIFIER).is(SPACING, b.nextNot(KEYWORDS), b.regexp(IDENTIFIER_START_REGEXP + IDENTIFIER_PART_REGEXP + "*+"));
        b.rule(PRIMITIVE).is(b.firstOf(UNDEFINED, NULL, TRUE, FALSE, STRING, NUMBER, DATE));

        b.rule(ASSIGNABLE).is(IDENTIFIER, b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)));
        b.rule(ASSIGNABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX)));
        b.rule(ASSIGNABLE_DEREFERENCE_POSTFIX).is(
                DOT, IDENTIFIER,
                b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)));
        b.rule(ASSIGNABLE_CALL_POSTFIX).is(CALL_OPERATOR, b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX));

        b.rule(CALLABLE).is(IDENTIFIER, b.firstOf(CALLABLE_CALL_POSTFIX, b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)));
        b.rule(CALLABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, CALLABLE_DEREFERENCE_POSTFIX);
        b.rule(CALLABLE_DEREFERENCE_POSTFIX).is(
                DOT, IDENTIFIER,
                b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX, CALLABLE_CALL_POSTFIX));
        b.rule(CALLABLE_CALL_POSTFIX).is(CALL_OPERATOR, b.optional(b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)));
    }

    private static void statements(LexerlessGrammarBuilder b) {
        b.rule(STATEMENT).is(b.firstOf(
            ASSIGNMENT_STATEMENT,
            CALL_STATEMENT,
            RETURN_STATEMENT,
            IF_STATEMENT,
            WHILE_STATEMENT,
            FOREACH_STATEMENT,
            FOR_STATEMENT,
            BREAK_STATEMENT,
            CONTINUE_STATEMENT,
            TRY_STATEMENT,
            RAISE_STATEMENT,
            EXECUTE_STATEMENT,
            ADD_HANDLER_STATEMENT,
            REMOVE_HANDLER_STATEMENT,
            LABEL_DEFINITION,
            GOTO_STATEMENT,
            EMPTY_STATEMENT)
        ).skipIfOneChild();

        b.rule(COMPOUND_STATEMENT).is(b.oneOrMore(STATEMENT, b.zeroOrMore(SEMICOLON, STATEMENT)));

        b.rule(ASSIGNMENT_STATEMENT).is(ASSIGNABLE, EQ, EXPRESSION);

        b.rule(CALL_STATEMENT).is(CALLABLE);

        b.rule(EMPTY_STATEMENT).is(SEMICOLON);

        b.rule(RETURN_STATEMENT).is(RETURN, b.optional(EXPRESSION));

        b.rule(IF_STATEMENT).is(
            IF, EXPRESSION, THEN,
            b.optional(COMPOUND_STATEMENT),
            b.zeroOrMore(ELSIF, EXPRESSION, THEN, b.optional(COMPOUND_STATEMENT)),
            b.optional(ELSE, b.optional(COMPOUND_STATEMENT)),
            END_IF);

        b.rule(WHILE_STATEMENT).is(
            WHILE, EXPRESSION, DO,
            b.optional(COMPOUND_STATEMENT),
            END_DO);

        b.rule(FOREACH_STATEMENT).is(
            FOR, EACH, IDENTIFIER, IN, EXPRESSION, DO,
            b.optional(COMPOUND_STATEMENT),
            END_DO);

        b.rule(FOR_STATEMENT).is(
            FOR, IDENTIFIER, EQ, EXPRESSION, TO, EXPRESSION, DO,
            b.optional(COMPOUND_STATEMENT),
            END_DO);

        b.rule(BREAK_STATEMENT).is(BREAK);
        b.rule(CONTINUE_STATEMENT).is(CONTINUE);

        b.rule(TRY_STATEMENT).is(
            TRY,
            COMPOUND_STATEMENT,
            EXCEPT,
            b.optional(COMPOUND_STATEMENT),
            END_TRY);

        b.rule(RAISE_STATEMENT).is(RAISE, EXPRESSION);

        b.rule(EXECUTE_STATEMENT).is(EXECUTE, LPAREN, EXPRESSION, RPAREN);

        b.rule(ADD_HANDLER_STATEMENT).is(
            word(b, "AddHandler", "ДобавитьОбработчик"),
            EXPRESSION, COMMA, EXPRESSION);

        b.rule(REMOVE_HANDLER_STATEMENT).is(
            word(b, "RemoveHandler", "УдалитьОбработчик"),
            EXPRESSION, COMMA, EXPRESSION);

        b.rule(GOTO_STATEMENT).is(GOTO, LABEL);
    }

    private static void directives(LexerlessGrammarBuilder b) {
        // TODO: Инструкции препроцессора
        // TODO: #Region / #Область ... #EndRegion / #КонецОбласти

        for (BslCompilationDirective directive : BslCompilationDirective.values())
            b.rule(directive).is(AMP, b.optional(b.regexp("[ \\t]+")), b.regexp(directive.getRegexp()), b.nextNot(IDENTIFIER_PART));

        b.rule(VAR_DIRECTIVE).is(b.firstOf(AT_CLIENT, AT_SERVER));
        b.rule(CALLABLE_DIRECTIVE).is(b.firstOf(
            AT_CLIENT,
            AT_SERVER,
            AT_SERVER_NO_CONTEXT,
            AT_CLIENT_AT_SERVER,
            AT_CLIENT_AT_SERVER_NO_CONTEXT));
    }

    private static void definitions(LexerlessGrammarBuilder b) {
        b.rule(VAR_DEFINITION).is(b.optional(VAR_DIRECTIVE), VAR, VARIABLE, b.zeroOrMore(b.sequence(COMMA, VARIABLE)), SEMICOLON);
        b.rule(VARIABLE).is(IDENTIFIER, b.optional(EXPORT));

        b.rule(CALLABLE_DEFINITION).is(b.firstOf(FUNCTION, PROCEDURE), CALLABLE_SIGNATURE, SEMICOLON);
        b.rule(CALLABLE_SIGNATURE).is(IDENTIFIER, CALLABLE_PARAMETERS, b.optional(EXPORT));
        b.rule(CALLABLE_PARAMETERS).is(LPAREN, b.zeroOrMore(PARAMETER, b.zeroOrMore(b.sequence(COMMA, PARAMETER))), RPAREN);

        b.rule(PARAMETER).is(b.optional(VAL), IDENTIFIER, b.optional(b.sequence(EQ, PARAMETER_DEFAULT_VALUE)));
        b.rule(PARAMETER_DEFAULT_VALUE).is(PRIMITIVE);

        b.rule(FUNC_DEFINITION).is(b.optional(CALLABLE_DIRECTIVE), FUNCTION, CALLABLE_SIGNATURE, b.zeroOrMore(VAR_DEFINITION), b.optional(COMPOUND_STATEMENT), END_FUNCTION);
        b.rule(PROC_DEFINITION).is(b.optional(CALLABLE_DIRECTIVE), PROCEDURE, CALLABLE_SIGNATURE, b.zeroOrMore(VAR_DEFINITION), b.optional(COMPOUND_STATEMENT), END_PROCEDURE);

        b.rule(LABEL_DEFINITION).is(LABEL, COLON);
        b.rule(LABEL).is(TILDA, b.regexp(IDENTIFIER_START_REGEXP + IDENTIFIER_PART_REGEXP + "*+"));
    }

    private static void keywords(LexerlessGrammarBuilder b) {
        BslKeyword[] keywords = BslKeyword.values();

        Object[] rest = new Object[keywords.length - 2];
        for (int i = 0; i < keywords.length; i++) {
            BslKeyword keyword = keywords[i];
            b.rule(keyword).is(SPACING, b.regexp(keyword.getRegexp()), b.nextNot(IDENTIFIER_PART));
            if (i > 1)
                rest[i - 2] = keywords[i];
        }

        b.rule(KEYWORDS).is(b.firstOf(keywords[0], keywords[1], rest));
    }

    private static void punctuators(LexerlessGrammarBuilder b) {
        for (BslPunctuator punctuator : BslPunctuator.values())
            b.rule(punctuator).is(SPACING, punctuator.getValue());
    }

    private static Object word(LexerlessGrammarBuilder b, String value, String valueRu) {
        return b.sequence(SPACING, b.regexp(BilingualUtils.caseInsensitiveRegexp(value, valueRu)), b.nextNot(IDENTIFIER_PART));
    }

}
