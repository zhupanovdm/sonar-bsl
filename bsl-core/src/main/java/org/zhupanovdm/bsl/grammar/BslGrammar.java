package org.zhupanovdm.bsl.grammar;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

import static com.sonar.sslr.api.GenericTokenType.EOF;
import static org.zhupanovdm.bsl.grammar.BslAsync.ASYNC;
import static org.zhupanovdm.bsl.grammar.BslAsync.AWAIT;
import static org.zhupanovdm.bsl.grammar.BslDirective.AT_CLIENT;
import static org.zhupanovdm.bsl.grammar.BslDirective.AT_SERVER;
import static org.zhupanovdm.bsl.grammar.BslKeyword.*;
import static org.zhupanovdm.bsl.grammar.BslPreprocessorInstruction.*;
import static org.zhupanovdm.bsl.grammar.BslPunctuator.*;
import static org.zhupanovdm.bsl.utils.BslGrammarUtils.group;
import static org.zhupanovdm.bsl.utils.BslGrammarUtils.word;

public enum BslGrammar implements GrammarRuleKey {
    SPACING, SPACING_NO_LB, WHITESPACE,
    KEYWORD,

    MODULE, BLOCK,

    VAR_DEF, FUNC_DEF, PROC_DEF, LABEL_DEFINITION,

    VARIABLE,
    SIGNATURE,
    PARAMETER,
    LABEL,

    STATEMENT,
    STATEMENT_NO_EMPTY,
    COMPOUND_STATEMENT,
    ASSIGNMENT_STATEMENT,
    CALL_STATEMENT,
    IF_STATEMENT,
    WHILE_STATEMENT,
    FOR_STATEMENT,
    FOREACH_STATEMENT,
    RAISE_STATEMENT,
    EXECUTE_STATEMENT,
    CONTINUE_STATEMENT,
    BREAK_STATEMENT,
    RETURN_STATEMENT,
    GOTO_STATEMENT,
    TRY_STATEMENT,
    ADD_HANDLER_STATEMENT,
    REMOVE_HANDLER_STATEMENT,
    EMPTY_STATEMENT,

    EXPRESSION,
    OR_EXPRESSION,
    AND_EXPRESSION,
    NOT_EXPRESSION,
    RELATIONAL_EXPRESSION,
    ADDITIVE_EXPRESSION,
    MULTIPLICATIVE_EXPRESSION,
    UNARY_EXPRESSION,
    POSTFIX_EXPRESSION,
    ASSIGNABLE_EXPRESSION,
    CALLABLE_EXPRESSION,
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

    INDEX_POSTFIX, DEREFERENCE_POSTFIX, CALL_POSTFIX,
    ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX,
    CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX, CALLABLE_CALL_POSTFIX,

    IDENTIFIER, IDENTIFIER_PART,
    FIELD,
    PRIMITIVE,
    STRING, STRING_PART,
    NUMBER,
    DATE,

    DIRECTIVE,
    PREPROCESSOR_INSTRUCTION, PP_CONDITION;

    public static final String COMMENT_REGEXP = "//[^\\n\\r]*+";

    public static final String NOSONAR_FLAG = "NOSONAR";

    public static final String STRING_REGEXP = "\\\"(?:[^\\\"\\r\\n]|\\\"{2}|(?:[\\r\\n]\\s*\\|))*\\\"";
    public static final String NUMBER_REGEXP = "[0-9]+(?:\\.(?:[0-9]++)?+)?";
    public static final String DATE_REGEXP = "'(?:\\d{8}(?:\\d{6})?|\\d{4}\\.\\d{2}\\.\\d{2}(?: \\d{2}:\\d{2}:\\d{2})?)'";

    private static final String LINE_TERMINATOR_REGEXP = "\\n\\r\\p{Zl}\\p{Zp}";
    private static final String WHITESPACE_REGEXP = "\\t\\v\\f\\u0020\\u00A0\\uFEFF\\p{Zs}";

    private static final String UNICODE_LETTER = "\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}";
    private static final String UNICODE_DIGIT = "\\p{Nd}";
    private static final String IDENTIFIER_START_REGEXP = "(?:[_" + UNICODE_LETTER + "])";
    private static final String IDENTIFIER_PART_REGEXP = "(?:" + IDENTIFIER_START_REGEXP + "|[" + UNICODE_DIGIT + "])";

    public static LexerlessGrammar createGrammar() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        b.rule(WHITESPACE).is(b.regexp("[" + LINE_TERMINATOR_REGEXP + WHITESPACE_REGEXP + "]*+"));
        b.rule(SPACING).is(
                b.skippedTrivia(WHITESPACE),
                b.zeroOrMore(b.commentTrivia(b.regexp(COMMENT_REGEXP)), b.skippedTrivia(WHITESPACE))
        ).skip();
        b.rule(SPACING_NO_LB).is(b.zeroOrMore(b.firstOf(
                b.skippedTrivia(b.regexp("[\\s&&[^\n\r]]++")),
                b.commentTrivia(b.regexp(COMMENT_REGEXP))
        ))).skip();

        b.rule(IDENTIFIER_PART).is(b.regexp(IDENTIFIER_PART_REGEXP));

        module(b);
        punctuators(b);
        keywords(b);
        literals(b);
        definitions(b);
        expressions(b);
        statements(b);
        directives(b);
        preprocessor(b);

        b.setRootRule(MODULE);

        return b.build();
    }

    private static void module(LexerlessGrammarBuilder b) {
        b.rule(MODULE).is(b.optional(BLOCK), SPACING, b.token(EOF, b.endOfInput()));
        b.rule(BLOCK).is(b.oneOrMore(b.firstOf(
                b.sequence(PP_REGION, IDENTIFIER, b.optional(BLOCK), PP_END_REGION),
                b.sequence(
                        PP_IF, PP_CONDITION, THEN, b.optional(BLOCK),
                        b.zeroOrMore(PP_ELSIF, PP_CONDITION, THEN, b.optional(BLOCK)),
                        PP_END_IF),
                b.firstOf(VAR_DEF, FUNC_DEF, PROC_DEF, COMPOUND_STATEMENT))
        ));
    }

    private static void literals(LexerlessGrammarBuilder b) {
        b.rule(STRING).is(STRING_PART, b.zeroOrMore(STRING_PART));
        b.rule(STRING_PART).is(SPACING, b.regexp(STRING_REGEXP));

        b.rule(NUMBER).is(SPACING, b.regexp(NUMBER_REGEXP));

        b.rule(DATE).is(SPACING, b.regexp(DATE_REGEXP));
    }

    private static void expressions(LexerlessGrammarBuilder b) {
        b.rule(EXPRESSION).is(OR_EXPRESSION);

        b.rule(OR_EXPRESSION).is(AND_EXPRESSION, b.optional(b.sequence(b.next(OR), LOGIC_OPERATOR), OR_EXPRESSION)).skipIfOneChild();
        b.rule(AND_EXPRESSION).is(NOT_EXPRESSION, b.optional(b.sequence(b.next(AND), LOGIC_OPERATOR), AND_EXPRESSION)).skipIfOneChild();
        b.rule(NOT_EXPRESSION).is(b.firstOf(b.sequence(b.next(NOT), LOGIC_OPERATOR, POSTFIX_EXPRESSION), RELATIONAL_EXPRESSION)).skipIfOneChild();
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
                DOT, FIELD,
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

        b.rule(IDENTIFIER).is(SPACING, b.nextNot(KEYWORD), b.regexp(IDENTIFIER_START_REGEXP + IDENTIFIER_PART_REGEXP + "*+"));
        b.rule(FIELD).is(SPACING, b.regexp(IDENTIFIER_START_REGEXP + IDENTIFIER_PART_REGEXP + "*+"));
        b.rule(PRIMITIVE).is(b.firstOf(UNDEFINED, NULL, TRUE, FALSE, STRING, NUMBER, DATE));

        b.rule(ASSIGNABLE_EXPRESSION).is(IDENTIFIER, b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)));
        b.rule(ASSIGNABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX)));
        b.rule(ASSIGNABLE_DEREFERENCE_POSTFIX).is(
                DOT, FIELD,
                b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)));
        b.rule(ASSIGNABLE_CALL_POSTFIX).is(CALL_OPERATOR, b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX));

        b.rule(CALLABLE_EXPRESSION).is(IDENTIFIER, b.firstOf(CALLABLE_CALL_POSTFIX, b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)));
        b.rule(CALLABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, CALLABLE_DEREFERENCE_POSTFIX);
        b.rule(CALLABLE_DEREFERENCE_POSTFIX).is(
                DOT, FIELD,
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

        b.rule(COMPOUND_STATEMENT).is(b.oneOrMore(b.firstOf(
                b.sequence(STATEMENT_NO_EMPTY, b.oneOrMore(EMPTY_STATEMENT), b.nextNot(STATEMENT_NO_EMPTY)),
                b.sequence(STATEMENT_NO_EMPTY, SEMICOLON, b.next(STATEMENT)),
                b.sequence(STATEMENT_NO_EMPTY, b.nextNot(STATEMENT_NO_EMPTY)),
                EMPTY_STATEMENT
        )));

        b.rule(EMPTY_STATEMENT).is(SEMICOLON);
        b.rule(STATEMENT_NO_EMPTY).is(b.nextNot(EMPTY_STATEMENT), STATEMENT).skip();
        b.rule(ASSIGNMENT_STATEMENT).is(ASSIGNABLE_EXPRESSION, EQ, b.optional(AWAIT), EXPRESSION);
        b.rule(CALL_STATEMENT).is(b.optional(AWAIT), CALLABLE_EXPRESSION);
        b.rule(AWAIT).is(SPACING, exactWord(b, AWAIT));

        b.rule(RETURN_STATEMENT).is(RETURN, b.optional(EXPRESSION));

        b.rule(IF_STATEMENT).is(
                IF, EXPRESSION, THEN,
                b.optional(BLOCK),
                b.zeroOrMore(ELSIF, EXPRESSION, THEN, b.optional(BLOCK)),
                b.optional(ELSE, b.optional(BLOCK)),
                END_IF);

        b.rule(WHILE_STATEMENT).is(
                WHILE, EXPRESSION, DO,
                b.optional(BLOCK),
                END_DO);

        b.rule(FOREACH_STATEMENT).is(
                FOR, EACH, IDENTIFIER, IN, EXPRESSION, DO,
                b.optional(BLOCK),
                END_DO);

        b.rule(FOR_STATEMENT).is(
                FOR, IDENTIFIER, EQ, EXPRESSION, TO, EXPRESSION, DO,
                b.optional(BLOCK),
                END_DO);

        b.rule(BREAK_STATEMENT).is(BREAK);
        b.rule(CONTINUE_STATEMENT).is(CONTINUE);

        b.rule(TRY_STATEMENT).is(
                TRY,
                BLOCK,
                EXCEPT,
                b.optional(BLOCK),
                END_TRY);

        b.rule(RAISE_STATEMENT).is(RAISE, EXPRESSION);

        b.rule(EXECUTE_STATEMENT).is(EXECUTE, LPAREN, EXPRESSION, RPAREN);

        b.rule(ADD_HANDLER_STATEMENT).is(
                b.sequence(SPACING, exactWord(b, "AddHandler", "ДобавитьОбработчик")),
                EXPRESSION, COMMA, EXPRESSION);

        b.rule(REMOVE_HANDLER_STATEMENT).is(
                b.sequence(SPACING, exactWord(b, "RemoveHandler", "УдалитьОбработчик")),
                EXPRESSION, COMMA, EXPRESSION);

        b.rule(GOTO_STATEMENT).is(GOTO, LABEL);
    }

    private static void directives(LexerlessGrammarBuilder b) {
        b.rule(DIRECTIVE).is(group(b, BslDirective.values(), w -> b.sequence(AMP, SPACING_NO_LB, exactWord(b, w))));
    }

    private static void preprocessor(LexerlessGrammarBuilder b) {
        b.rule(PREPROCESSOR_INSTRUCTION).is(group(b, BslPreprocessorInstruction.values(), w -> b.sequence(HASH, SPACING_NO_LB, exactWord(b, w))));
        b.rule(PP_CONDITION).is(b.optional(NOT), IDENTIFIER, b.zeroOrMore(b.firstOf(OR, AND), b.optional(NOT), IDENTIFIER));
    }

    private static void definitions(LexerlessGrammarBuilder b) {
        b.rule(VAR_DEF).is(
                b.optional(b.next(b.firstOf(AT_CLIENT, AT_SERVER)), DIRECTIVE),
                VAR, VARIABLE, b.zeroOrMore(COMMA, VARIABLE), SEMICOLON);
        b.rule(VARIABLE).is(IDENTIFIER, b.optional(EXPORT));

        b.rule(FUNC_DEF).is(
                b.optional(b.firstOf(
                        b.sequence(b.nextNot(AT_CLIENT), DIRECTIVE),
                        b.sequence(AT_CLIENT, b.optional(ASYNC)))
                ),
                FUNCTION, SIGNATURE,
                b.optional(BLOCK),
                END_FUNCTION);
        b.rule(PROC_DEF).is(
                b.optional(b.firstOf(
                        b.sequence(b.nextNot(AT_CLIENT), DIRECTIVE),
                        b.sequence(AT_CLIENT, b.optional(ASYNC)))
                ),
                PROCEDURE, SIGNATURE,
                b.optional(BLOCK),
                END_PROCEDURE);
        b.rule(SIGNATURE).is(IDENTIFIER, LPAREN, b.optional(PARAMETER, b.zeroOrMore(COMMA, PARAMETER)), RPAREN, b.optional(EXPORT));
        b.rule(ASYNC).is(SPACING, exactWord(b, ASYNC));

        b.rule(PARAMETER).is(
                b.optional(VAL), IDENTIFIER,
                b.optional(b.sequence(EQ, PRIMITIVE)));

        b.rule(LABEL_DEFINITION).is(LABEL, COLON);
        b.rule(LABEL).is(TILDA, b.regexp(IDENTIFIER_START_REGEXP + IDENTIFIER_PART_REGEXP + "*+"));
    }

    private static void keywords(LexerlessGrammarBuilder b) {
        b.rule(KEYWORD).is(group(b, BslKeyword.values(), w -> b.sequence(SPACING, exactWord(b, w))));
    }

    private static void punctuators(LexerlessGrammarBuilder b) {
        for (BslPunctuator punctuator : BslPunctuator.values())
            b.rule(punctuator).is(SPACING, punctuator.getValue());
    }

    private static Object exactWord(LexerlessGrammarBuilder b, BslWord bslWord) {
        return b.sequence(word(b, bslWord), b.nextNot(IDENTIFIER_PART));
    }

    private static Object exactWord(LexerlessGrammarBuilder b, String value, String valueRu) {
        return b.sequence(word(b, value, valueRu), b.nextNot(IDENTIFIER_PART));
    }

}
