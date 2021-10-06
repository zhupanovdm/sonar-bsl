package org.zhupanovdm.bsl.grammar;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static com.sonar.sslr.api.GenericTokenType.EOF;
import static org.zhupanovdm.bsl.grammar.BslKeyword.*;
import static org.zhupanovdm.bsl.grammar.BslPunctuator.*;
import static org.zhupanovdm.bsl.utils.GrammarUtils.bilingual;

public enum BslGrammar implements GrammarRuleKey {
    SPACING, SPACING_NO_LB, LB,
    KEYWORD,

    MODULE, BODY,

    VAR_DEF, FUNC_DEF, PROC_DEF, LABEL_DEF,

    VARIABLE,
    VARIABLE_LOCAL,
    SIGNATURE,
    PARAMETER,
    LABEL,

    STATEMENT,
    STATEMENT_NOT_EMPTY,
    COMPOUND_STMT,
    ASSIGN_STMT,
    CALL_STMT,
    IF_STMT,
    WHILE_STMT,
    FOR_STMT,
    FOREACH_STMT,
    RAISE_STMT,
    EXECUTE_STMT,
    CONTINUE_STMT,
    BREAK_STMT,
    RETURN_STMT,
    GOTO_STMT,
    TRY_STMT,
    EMPTY_STMT,
    ADD_HANDLER_STMT,
    REMOVE_HANDLER_STMT,

    EXPRESSION,
    OR_EXPR,
    AND_EXPR,
    NOT_EXPR,
    RELATIONAL_EXPR,
    ADDITIVE_EXPR,
    MULTIPLICATIVE_EXPR,
    UNARY_EXPR,
    POSTFIX_EXPR,
    TERNARY_EXPR,
    PRIMARY_EXPR,
    NEW_EXPR,
    ASSIGNABLE_EXPR,
    CALLABLE_EXPR,

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
    CONSTRUCTOR_CALL_POSTFIX,

    IDENTIFIER,
    FIELD,
    PRIMITIVE,
    STRING, STRING_CONTENT, STRING_LITERAL,
    NUMBER,
    DATE,

    DIRECTIVE,

    PP_IF, PP_ELSIF, PP_END_IF,
    PP_CONDITION, PP_OR_EXPRESSION, PP_AND_EXPRESSION, PP_NOT_EXPRESSION, PP_PRIMARY_EXPRESSION,

    REGION, END_REGION;

    public static LexerlessGrammar create() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
        b.setRootRule(MODULE);

        lexical(b);
        module(b);
        punctuators(b);
        keywords(b);
        literals(b);
        definitions(b);
        expressions(b);
        statements(b);
        directives(b);
        preprocessor(b);

        return b.build();
    }

    private static void lexical(LexerlessGrammarBuilder b) {
        b.rule(SPACING).is(whitespace(b), b.zeroOrMore(comment(b), whitespace(b))).skip();
        b.rule(SPACING_NO_LB).is(b.zeroOrMore(b.firstOf(b.skippedTrivia(b.regexp("[\\s&&[^\n\r]]+")), comment(b)))).skip();
        b.rule(LB).is(b.regexp("[\n\r]+"));
    }

    private static void module(LexerlessGrammarBuilder b) {
        b.rule(MODULE).is(b.optional(BODY), SPACING, b.token(EOF, b.endOfInput()));
        b.rule(BODY).is(b.oneOrMore(b.firstOf(
                b.sequence(b.sequence(REGION, IDENTIFIER), b.optional(BODY), END_REGION),
                b.sequence(
                        PP_IF, PP_CONDITION, THEN, b.optional(BODY),
                        b.zeroOrMore(PP_ELSIF, PP_CONDITION, THEN, b.optional(BODY)),
                        PP_END_IF),
                b.firstOf(VAR_DEF, FUNC_DEF, PROC_DEF, COMPOUND_STMT))
        ));
    }

    private static void literals(LexerlessGrammarBuilder b) {
        b.rule(STRING).is(b.oneOrMore(SPACING, STRING_LITERAL));
        b.rule(STRING_CONTENT).is(b.regexp("(?:[^\\\"\\r\\n]|\\\"{2})*+"));
        b.rule(STRING_LITERAL).is(
                QUOTE,
                STRING_CONTENT,
                b.zeroOrMore(b.skippedTrivia(LB), whitespace(b), PIPE, STRING_CONTENT),
                QUOTE);

        b.rule(NUMBER).is(SPACING, b.regexp("[+-]?[0-9]+(?:\\.(?:[0-9]++)?+)?"));

        b.rule(DATE).is(SPACING, b.regexp("'(?:\\d{8}(?:\\d{6})?|\\d{4}\\.\\d{2}\\.\\d{2}(?: \\d{2}:\\d{2}:\\d{2})?)'"));
    }

    private static void expressions(LexerlessGrammarBuilder b) {
        b.rule(EXPRESSION).is(OR_EXPR).skip();

        b.rule(OR_EXPR).is(AND_EXPR, b.optional(b.sequence(b.next(OR), LOGIC_OPERATOR), OR_EXPR)).skipIfOneChild();
        b.rule(AND_EXPR).is(NOT_EXPR, b.optional(b.sequence(b.next(AND), LOGIC_OPERATOR), AND_EXPR)).skipIfOneChild();
        b.rule(NOT_EXPR).is(b.firstOf(b.sequence(b.next(NOT), LOGIC_OPERATOR, POSTFIX_EXPR), RELATIONAL_EXPR)).skipIfOneChild();
        b.rule(LOGIC_OPERATOR).is(b.firstOf(OR, AND, NOT));

        b.rule(RELATIONAL_EXPR).is(ADDITIVE_EXPR, b.optional(RELATIONAL_OPERATOR, RELATIONAL_EXPR)).skipIfOneChild();
        b.rule(RELATIONAL_OPERATOR).is(b.firstOf(NEQ, LE, GE, GT, LT, EQ));

        b.rule(ADDITIVE_EXPR).is(MULTIPLICATIVE_EXPR, b.optional(ADDITIVE_OPERATOR, ADDITIVE_EXPR)).skipIfOneChild();
        b.rule(ADDITIVE_OPERATOR).is(b.firstOf(PLUS, MINUS));

        b.rule(MULTIPLICATIVE_EXPR).is(UNARY_EXPR, b.optional(MULTIPLICATIVE_OPERATOR, MULTIPLICATIVE_EXPR)).skipIfOneChild();
        b.rule(MULTIPLICATIVE_OPERATOR).is(b.firstOf(STAR, SLASH, PERCENT));

        b.rule(UNARY_EXPR).is(b.firstOf(b.sequence(UNARY_OPERATOR, POSTFIX_EXPR), POSTFIX_EXPR)).skipIfOneChild();
        b.rule(UNARY_OPERATOR).is(b.firstOf(PLUS, MINUS));

        b.rule(POSTFIX_EXPR).is(b.optional(AWAIT), b.firstOf(
                b.sequence(b.next(IDENTIFIER), PRIMARY_EXPR, b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX, CALL_POSTFIX)),
                PRIMARY_EXPR,
                NEW_EXPR,
                TERNARY_EXPR)
        ).skipIfOneChild();
        b.rule(INDEX_POSTFIX).is(INDEX_OPERATOR, b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX)));
        b.rule(DEREFERENCE_POSTFIX).is(
                DOT, FIELD,
                b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX, CALL_POSTFIX)));
        b.rule(CALL_POSTFIX).is(CALL_OPERATOR, b.optional(b.firstOf(INDEX_POSTFIX, DEREFERENCE_POSTFIX)));

        b.rule(INDEX_OPERATOR).is(LBRACK, EXPRESSION, RBRACK);
        b.rule(CALL_OPERATOR).is(LPAREN, b.optional(EXPRESSION), b.zeroOrMore(COMMA, b.optional(EXPRESSION)), RPAREN);

        b.rule(TERNARY_EXPR).is(
                QUESTION, LPAREN,
                EXPRESSION, COMMA,
                EXPRESSION, COMMA,
                EXPRESSION, RPAREN);

        b.rule(NEW_EXPR).is(NEW, IDENTIFIER, b.optional(CONSTRUCTOR_CALL_POSTFIX));
        b.rule(CONSTRUCTOR_CALL_POSTFIX).is(CALL_OPERATOR);

        b.rule(PRIMARY_EXPR).is(b.firstOf(IDENTIFIER, PRIMITIVE, b.sequence(LPAREN, EXPRESSION, RPAREN)));

        b.rule(IDENTIFIER).is(SPACING, b.nextNot(KEYWORD), identifier(b));
        b.rule(FIELD).is(SPACING, identifier(b));
        b.rule(PRIMITIVE).is(b.firstOf(UNDEFINED, NULL, TRUE, FALSE, STRING, NUMBER, DATE));

        b.rule(ASSIGNABLE_EXPR).is(b.firstOf(
                b.sequence(b.next(IDENTIFIER), PRIMARY_EXPR, b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)),
                VARIABLE_LOCAL
                ));
        b.rule(ASSIGNABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX)));
        b.rule(ASSIGNABLE_DEREFERENCE_POSTFIX).is(
                DOT, FIELD,
                b.optional(b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX, ASSIGNABLE_CALL_POSTFIX)));
        b.rule(ASSIGNABLE_CALL_POSTFIX).is(CALL_OPERATOR, b.firstOf(ASSIGNABLE_INDEX_POSTFIX, ASSIGNABLE_DEREFERENCE_POSTFIX));

        b.rule(CALLABLE_EXPR).is(b.optional(AWAIT), b.next(IDENTIFIER), PRIMARY_EXPR, b.firstOf(CALLABLE_CALL_POSTFIX, b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)));
        b.rule(CALLABLE_INDEX_POSTFIX).is(INDEX_OPERATOR, CALLABLE_DEREFERENCE_POSTFIX);
        b.rule(CALLABLE_DEREFERENCE_POSTFIX).is(
                DOT, FIELD,
                b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX, CALLABLE_CALL_POSTFIX));
        b.rule(CALLABLE_CALL_POSTFIX).is(CALL_OPERATOR, b.optional(b.firstOf(CALLABLE_INDEX_POSTFIX, CALLABLE_DEREFERENCE_POSTFIX)));
    }

    private static void statements(LexerlessGrammarBuilder b) {
        b.rule(STATEMENT).is(b.firstOf(
                ASSIGN_STMT,
                CALL_STMT,
                RETURN_STMT,
                IF_STMT,
                WHILE_STMT,
                FOREACH_STMT,
                FOR_STMT,
                BREAK_STMT,
                CONTINUE_STMT,
                TRY_STMT,
                RAISE_STMT,
                EXECUTE_STMT,
                ADD_HANDLER_STMT,
                REMOVE_HANDLER_STMT,
                GOTO_STMT,
                EMPTY_STMT)
        ).skipIfOneChild();

        b.rule(COMPOUND_STMT).is(b.oneOrMore(b.firstOf(
                LABEL_DEF,
                b.sequence(STATEMENT_NOT_EMPTY, b.oneOrMore(EMPTY_STMT), b.nextNot(STATEMENT_NOT_EMPTY)),
                b.sequence(STATEMENT_NOT_EMPTY, SEMICOLON, b.next(STATEMENT)),
                b.sequence(STATEMENT_NOT_EMPTY, b.nextNot(STATEMENT_NOT_EMPTY)),
                EMPTY_STMT
        )));

        b.rule(EMPTY_STMT).is(SEMICOLON);
        b.rule(STATEMENT_NOT_EMPTY).is(b.nextNot(EMPTY_STMT), STATEMENT).skip();
        b.rule(ASSIGN_STMT).is(ASSIGNABLE_EXPR, EQ, EXPRESSION);
        b.rule(CALL_STMT).is(CALLABLE_EXPR);

        b.rule(RETURN_STMT).is(RETURN, b.optional(EXPRESSION));

        b.rule(IF_STMT).is(
                IF, EXPRESSION, THEN,
                b.optional(BODY),
                b.zeroOrMore(ELSIF, EXPRESSION, THEN, b.optional(BODY)),
                b.optional(ELSE, b.optional(BODY)),
                END_IF);

        b.rule(WHILE_STMT).is(
                WHILE, EXPRESSION, DO,
                b.optional(BODY),
                END_DO);

        b.rule(FOREACH_STMT).is(
                FOR, EACH, VARIABLE_LOCAL, IN, EXPRESSION, DO,
                b.optional(BODY),
                END_DO);

        b.rule(FOR_STMT).is(
                FOR, VARIABLE_LOCAL, EQ, EXPRESSION, TO, EXPRESSION, DO,
                b.optional(BODY),
                END_DO);

        b.rule(BREAK_STMT).is(BREAK);
        b.rule(CONTINUE_STMT).is(CONTINUE);

        b.rule(TRY_STMT).is(
                TRY,
                BODY,
                EXCEPT,
                b.optional(BODY),
                END_TRY);

        b.rule(RAISE_STMT).is(RAISE, b.optional(EXPRESSION));

        b.rule(EXECUTE_STMT).is(EXECUTE, LPAREN, EXPRESSION, RPAREN);

        b.rule(ADD_HANDLER_STMT).is(
                b.sequence(SPACING, keyword(b, ADD_HANDLER)),
                EXPRESSION, COMMA, EXPRESSION);

        b.rule(REMOVE_HANDLER_STMT).is(
                b.sequence(SPACING, keyword(b, REMOVE_HANDLER)),
                EXPRESSION, COMMA, EXPRESSION);

        b.rule(GOTO_STMT).is(GOTO, LABEL);
    }

    private static void directives(LexerlessGrammarBuilder b) {
        b.rule(DIRECTIVE).is(AMP, SPACING_NO_LB, wordGroup(b, BslDirective.values(), w -> keyword(b, w)));
    }

    private static void preprocessor(LexerlessGrammarBuilder b) {
        b.rule(PP_IF).is(HASH, SPACING_NO_LB, bilingual(b, IF.getValue(), IF.getValueAlt()));
        b.rule(PP_ELSIF).is(HASH, SPACING_NO_LB, bilingual(b, ELSIF.getValue(), ELSIF.getValueAlt()));
        b.rule(PP_END_IF).is(HASH, SPACING_NO_LB, bilingual(b, END_IF.getValue(), END_IF.getValueAlt()));

        b.rule(REGION).is(HASH, SPACING_NO_LB, bilingual(b, "Region", "Область"));
        b.rule(END_REGION).is(HASH, SPACING_NO_LB, bilingual(b, "EndRegion", "КонецОбласти"));

        b.rule(PP_CONDITION).is(PP_OR_EXPRESSION).skip();

        b.rule(PP_OR_EXPRESSION).is(PP_AND_EXPRESSION, b.optional(b.sequence(b.next(OR), LOGIC_OPERATOR), PP_OR_EXPRESSION)).skipIfOneChild();
        b.rule(PP_AND_EXPRESSION).is(PP_NOT_EXPRESSION, b.optional(b.sequence(b.next(AND), LOGIC_OPERATOR), PP_AND_EXPRESSION)).skipIfOneChild();
        b.rule(PP_NOT_EXPRESSION).is(b.firstOf(b.sequence(b.next(NOT), LOGIC_OPERATOR, PP_PRIMARY_EXPRESSION), PP_PRIMARY_EXPRESSION)).skipIfOneChild();
        b.rule(PP_PRIMARY_EXPRESSION).is(IDENTIFIER);
    }

    private static void definitions(LexerlessGrammarBuilder b) {
        b.rule(VAR_DEF).is(b.optional(DIRECTIVE), VAR, VARIABLE, b.zeroOrMore(COMMA, VARIABLE), SEMICOLON);
        b.rule(VARIABLE).is(IDENTIFIER, b.optional(EXPORT));
        b.rule(VARIABLE_LOCAL).is(IDENTIFIER);

        b.rule(FUNC_DEF).is(
                b.optional(DIRECTIVE),
                b.optional(ASYNC),
                FUNCTION, SIGNATURE,
                b.optional(EXPORT),
                b.optional(BODY),
                END_FUNCTION);
        b.rule(PROC_DEF).is(
                b.optional(DIRECTIVE),
                b.optional(ASYNC),
                PROCEDURE, SIGNATURE,
                b.optional(EXPORT),
                b.optional(BODY),
                END_PROCEDURE);
        b.rule(SIGNATURE).is(IDENTIFIER, LPAREN, b.optional(PARAMETER, b.zeroOrMore(COMMA, PARAMETER)), RPAREN).skip();
        b.rule(PARAMETER).is(b.optional(VAL), IDENTIFIER, b.optional(EQ, PRIMITIVE));

        b.rule(LABEL_DEF).is(LABEL, COLON);
        b.rule(LABEL).is(TILDA, identifier(b));
    }

    private static void keywords(LexerlessGrammarBuilder b) {
        b.rule(KEYWORD).is(wordGroup(b, BslKeyword.keywords(), w -> b.sequence(SPACING, keyword(b, w))));

        b.rule(ASYNC).is(SPACING, keyword(b, ASYNC));
        b.rule(AWAIT).is(SPACING, keyword(b, AWAIT));

        b.rule(ADD_HANDLER).is(SPACING, keyword(b, ADD_HANDLER));
        b.rule(REMOVE_HANDLER).is(SPACING, keyword(b, REMOVE_HANDLER));
    }

    private static void punctuators(LexerlessGrammarBuilder b) {
        for (BslPunctuator punctuator : BslPunctuator.values()) {
            b.rule(punctuator).is(SPACING, punctuator.getValue());
        }
    }

    private static Object keyword(LexerlessGrammarBuilder b, String value, String valueAlt) {
        return b.sequence(bilingual(b, value, valueAlt), b.nextNot(b.regexp("[\\p{javaJavaIdentifierPart}&&[^$]]")));
    }

    private static Object keyword(LexerlessGrammarBuilder b, BslWord bslWord) {
        return keyword(b, bslWord.getValue(), bslWord.getValueAlt());
    }

    private static Object comment(LexerlessGrammarBuilder b) {
        return b.commentTrivia(b.regexp("//[^\\n\\r]*+"));
    }

    private static Object identifier(LexerlessGrammarBuilder b) {
        return b.regexp("[\\p{javaJavaIdentifierStart}&&[^$]]+[\\p{javaJavaIdentifierPart}&&[^$]]*+");
    }

    private static Object whitespace(LexerlessGrammarBuilder b) {
        return b.skippedTrivia(b.regexp("[\\s]*+"));
    }

    private static Object wordGroup(LexerlessGrammarBuilder b, BslWord[] words, Function<BslWord, Object> ruleMapper) {
        if (words.length == 0) {
            throw new NoSuchElementException("No words are passed");
        }

        for (BslWord word : words) {
            b.rule(word).is(ruleMapper.apply(word));
        }

        if (words.length == 1) {
            return words[0];
        } else if (words.length == 2) {
            return b.firstOf(words[0], words[1]);
        }

        return b.firstOf(words[0], words[1], (Object[]) Arrays.copyOfRange(words, 2, words.length));
    }
}
