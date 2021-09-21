package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslKeyword.*;
import static org.zhupanovdm.bsl.grammar.BslGrammar.KEYWORD;

public class KeywordTest {

    private final LexerlessGrammar g = BslGrammar.create();

    @Test
    public void keywords() {
        assertThat(g.rule(KEYWORD))
                .matches("If")
                .matches("Then")
                .matches("ElsIf")
                .matches("Else")
                .matches("EndIf")
                .matches("For")
                .matches("each")
                .matches("In")
                .matches("To")
                .matches("While")
                .matches("Do")
                .matches("EndDo")
                .matches("Procedure")
                .matches("EndProcedure")
                .matches("Function")
                .matches("EndFunction")
                .matches("Val")
                .matches("Var")
                .matches("Export")
                .matches("Goto")
                .matches("Return")
                .matches("Continue")
                .matches("Break")
                .matches("And")
                .matches("Or")
                .matches("Not")
                .matches("Try")
                .matches("Except")
                .matches("Raise")
                .matches("EndTry")
                .matches("New")
                .matches("Execute")
                .matches("True")
                .matches("False")
                .matches("Undefined")
                .matches("Null")
                .notMatches("Async")
                .notMatches("Await")
                .notMatches("AddHandler")
                .notMatches("RemoveHandler")
                .notMatches("if2")
                .notMatches("if_")
                .notMatches("IfNot");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(IF)).matches("If").matches("Если");
        assertThat(g.rule(THEN)).matches("Then").matches("Тогда");
        assertThat(g.rule(ELSIF)).matches("ElsIf").matches("ИначеЕсли");
        assertThat(g.rule(ELSE)).matches("Else").matches("Иначе");
        assertThat(g.rule(END_IF)).matches("EndIf").matches("КонецЕсли");
        assertThat(g.rule(FOR)).matches("For").matches("Для");
        assertThat(g.rule(EACH)).matches("each").matches("каждого");
        assertThat(g.rule(IN)).matches("In").matches("Из");
        assertThat(g.rule(TO)).matches("To").matches("По");
        assertThat(g.rule(WHILE)).matches("While").matches("Пока");
        assertThat(g.rule(DO)).matches("Do").matches("Цикл");
        assertThat(g.rule(END_DO)).matches("EndDo").matches("КонецЦикла");
        assertThat(g.rule(PROCEDURE)).matches("Procedure").matches("Процедура");
        assertThat(g.rule(END_PROCEDURE)).matches("EndProcedure").matches("КонецПроцедуры");
        assertThat(g.rule(FUNCTION)).matches("Function").matches("Функция");
        assertThat(g.rule(END_FUNCTION)).matches("EndFunction").matches("КонецФункции");
        assertThat(g.rule(VAL)).matches("Val").matches("Знач");
        assertThat(g.rule(VAR)).matches("Var").matches("Перем");
        assertThat(g.rule(EXPORT)).matches("Export").matches("Экспорт");
        assertThat(g.rule(GOTO)).matches("Goto").matches("Перейти");
        assertThat(g.rule(RETURN)).matches("Return").matches("Возврат");
        assertThat(g.rule(CONTINUE)).matches("Continue").matches("Продолжить");
        assertThat(g.rule(BREAK)).matches("Break").matches("Прервать");
        assertThat(g.rule(AND)).matches("And").matches("И");
        assertThat(g.rule(OR)).matches("Or").matches("Или");
        assertThat(g.rule(NOT)).matches("Not").matches("Не");
        assertThat(g.rule(TRY)).matches("Try").matches("Попытка");
        assertThat(g.rule(EXCEPT)).matches("Except").matches("Исключение");
        assertThat(g.rule(RAISE)).matches("Raise").matches("ВызватьИсключение");
        assertThat(g.rule(END_TRY)).matches("EndTry").matches("КонецПопытки");
        assertThat(g.rule(NEW)).matches("New").matches("Новый");
        assertThat(g.rule(EXECUTE)).matches("Execute").matches("Выполнить");
        assertThat(g.rule(TRUE)).matches("True").matches("Истина");
        assertThat(g.rule(FALSE)).matches("False").matches("Ложь");
        assertThat(g.rule(UNDEFINED)).matches("Undefined").matches("Неопределено");
        assertThat(g.rule(NULL)).matches("Null");

        assertThat(g.rule(ASYNC)).matches("Async").matches("Асинх");
        assertThat(g.rule(AWAIT)).matches("Await").matches("Ждать");

        assertThat(g.rule(ADD_HANDLER)).matches("AddHandler").matches("ДобавитьОбработчик");
        assertThat(g.rule(REMOVE_HANDLER)).matches("RemoveHandler").matches("УдалитьОбработчик");

        assertThat(BslKeyword.keywords()).hasSize(36);
    }

}
