package org.zhupanovdm.bsl.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.zhupanovdm.bsl.grammar.BslGrammar.KEYWORD;
import static org.zhupanovdm.bsl.grammar.BslKeyword.*;

public class KeywordTest {

    private final LexerlessGrammar g = BslGrammar.createGrammar();

    @Test
    public void keywords() {
        assertThat(g.rule(KEYWORD))
                .matches("if")
                .matches("then")
                .matches("elsif")
                .matches("else")
                .matches("endif")
                .matches("for")
                .matches("each")
                .matches("in")
                .matches("to")
                .matches("while")
                .matches("do")
                .matches("enddo")
                .matches("procedure")
                .matches("endprocedure")
                .matches("function")
                .matches("endfunction")
                .matches("val")
                .matches("var")
                .matches("export")
                .matches("goto")
                .matches("return")
                .matches("continue")
                .matches("break")
                .matches("and")
                .matches("or")
                .matches("not")
                .matches("try")
                .matches("except")
                .matches("raise")
                .matches("endtry")
                .matches("new")
                .matches("execute")
                .matches("true")
                .matches("false")
                .matches("undefined")
                .matches("null");

        assertThat(g.rule(KEYWORD))
                .notMatches("if2")
                .notMatches("ifid")
                .notMatches("ifnot");
    }

    @Test
    public void bilingual() {
        assertThat(g.rule(IF)).matches("if").matches("Если");
        assertThat(g.rule(THEN)).matches("then").matches("Тогда");
        assertThat(g.rule(ELSIF)).matches("elsif").matches("ИначеЕсли");
        assertThat(g.rule(ELSE)).matches("else").matches("Иначе");
        assertThat(g.rule(END_IF)).matches("endif").matches("КонецЕсли");
        assertThat(g.rule(FOR)).matches("for").matches("Для");
        assertThat(g.rule(EACH)).matches("each").matches("Каждого");
        assertThat(g.rule(IN)).matches("in").matches("Из");
        assertThat(g.rule(TO)).matches("to").matches("По");
        assertThat(g.rule(WHILE)).matches("while").matches("Пока");
        assertThat(g.rule(DO)).matches("do").matches("Цикл");
        assertThat(g.rule(END_DO)).matches("enddo").matches("КонецЦикла");
        assertThat(g.rule(PROCEDURE)).matches("procedure").matches("Процедура");
        assertThat(g.rule(END_PROCEDURE)).matches("endprocedure").matches("КонецПроцедуры");
        assertThat(g.rule(FUNCTION)).matches("function").matches("Функция");
        assertThat(g.rule(END_FUNCTION)).matches("endfunction").matches("КонецФункции");
        assertThat(g.rule(VAL)).matches("val").matches("Знач");
        assertThat(g.rule(VAR)).matches("var").matches("Перем");
        assertThat(g.rule(EXPORT)).matches("export").matches("Экспорт");
        assertThat(g.rule(GOTO)).matches("goto").matches("Перейти");
        assertThat(g.rule(RETURN)).matches("return").matches("Возврат");
        assertThat(g.rule(CONTINUE)).matches("continue").matches("Продолжить");
        assertThat(g.rule(BREAK)).matches("break").matches("Прервать");
        assertThat(g.rule(AND)).matches("and").matches("И");
        assertThat(g.rule(OR)).matches("or").matches("Или");
        assertThat(g.rule(NOT)).matches("not").matches("Не");
        assertThat(g.rule(TRY)).matches("try").matches("Попытка");
        assertThat(g.rule(EXCEPT)).matches("except").matches("Исключение");
        assertThat(g.rule(RAISE)).matches("raise").matches("ВызватьИсключение");
        assertThat(g.rule(END_TRY)).matches("endtry").matches("КонецПопытки");
        assertThat(g.rule(NEW)).matches("new").matches("Новый");
        assertThat(g.rule(EXECUTE)).matches("execute").matches("Выполнить");
        assertThat(g.rule(TRUE)).matches("true").matches("Истина");
        assertThat(g.rule(FALSE)).matches("false").matches("Ложь");
        assertThat(g.rule(UNDEFINED)).matches("undefined").matches("Неопределено");
        assertThat(g.rule(NULL)).matches("null");

        assertThat(values()).hasSize(36);
    }

}
