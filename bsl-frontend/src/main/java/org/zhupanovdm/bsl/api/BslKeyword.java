package org.zhupanovdm.bsl.api;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public enum BslKeyword implements BslWord {
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
    NULL(null),

    ASYNC("Асинх", true),
    AWAIT("Ждать", true),

    ADD_HANDLER("ДобавитьОбработчик", true),
    REMOVE_HANDLER("УдалитьОбработчик", true);

    private final String valueAlt;
    private final boolean syntactic;

    BslKeyword(@Nullable String valueAlt) {
        this(valueAlt, false);
    }

    BslKeyword(@Nullable String valueAlt, boolean syntactic) {
        this.valueAlt = valueAlt;
        this.syntactic = syntactic;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return BslWord.toValue(this);
    }

    @CheckForNull
    @Override
    public String getValueAlt() {
        return valueAlt;
    }

    public boolean isSyntactic() {
        return syntactic;
    }

    public static BslKeyword[] keywords() {
        List<BslKeyword> list = new LinkedList<>();
        for (BslKeyword keyword : values()) {
            if (!keyword.isSyntactic()) {
                list.add(keyword);
            }
        }
        return list.toArray(new BslKeyword[0]);
    }

}
