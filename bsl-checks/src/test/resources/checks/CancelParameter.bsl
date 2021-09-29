//
Функция ПриЗаписи(Объект, Отмена, Чтото) // Noncompliant {{Параметр 'Отмена' должен быть последним параметром процедуры\функции.}}

    Если Объект.ЕстьОшибка Тогда
        Отмена = 1; // Noncompliant {{Параметру 'Отмена' должно присваиваться только значение Истина.}}
        Возврат Ложь
    КонецЕсли;

    Возврат Истина

КонецФункции

//
Процедура ПриЗаписи(Объект, ОТМЕНА, Чтото) // Noncompliant

    Если Объект.ЕстьОшибка Тогда
        отмена = 1 // Noncompliant
    КонецЕсли

КонецПроцедуры

//
Процедура ПриЗаписи(Объект, Отмена) // OK. Последний параметр

    Если Объект.ЕстьОшибка Тогда
        Отмена = Истина // OK. Присвоена Истина
    КонецЕсли

КонецПроцедуры

//
Процедура ПриЗаписи(Объект) // OK. Нет параметра 'Отмена'

    Если Объект.ЕстьОшибка Тогда
        Отмена = 1 // OK. Локальная перменная, не является параметром
    КонецЕсли

КонецПроцедуры

Отмена = 1 // OK. Переменная вне тела процедуры\функции, не является параметром