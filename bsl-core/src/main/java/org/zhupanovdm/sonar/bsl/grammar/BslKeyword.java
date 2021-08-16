package org.zhupanovdm.sonar.bsl.grammar;

import com.sonar.sslr.api.AstNode;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;

public enum BslKeyword implements BilingualWord, GrammarRuleKey {
    IF("Если"),
    THEN("Тогда"),
    ELSIF("ИначеЕсли"),
    ELSE("Иначе"),
    END_IF("КонецЕсли"),
    FOR("Для"),
    EACH("Каждого"),
    IN("Из"),
    TO("По"),
    WHILE("Пока"),
    DO("Цикл"),
    END_DO("КонецЦикла"),
    PROCEDURE("Процедура"),
    END_PROCEDURE("КонецПроцедуры"),
    FUNCTION("Функция"),
    END_FUNCTION("КонецФункции"),
    VAL("Знач"),
    VAR("Перем"),
    EXPORT("Экспорт"),
    GOTO("Перейти"),
    RETURN("Возврат"),
    CONTINUE("Продолжить"),
    BREAK("Прервать"),
    AND("И"),
    OR("Или"),
    NOT("Не"),
    TRY("Попытка"),
    EXCEPT("Исключение"),
    RAISE("ВызватьИсключение"),
    END_TRY("КонецПопытки"),
    NEW("Новый"),
    EXECUTE("Выполнить"),
    TRUE("Истина"),
    FALSE("Ложь"),
    UNDEFINED("Неопределено"),
    NULL;

    private final String valueRu;

    BslKeyword() {
        this(null);
    }

    BslKeyword(@Nullable String valueRu) {
        this.valueRu = valueRu;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return name().replaceAll("_", "");
    }

    @Override
    public String getValueRu() {
        return valueRu;
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
        return false;
    }
}
