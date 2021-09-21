#Если Сервер И ВнешнееСоединение Тогда

&НаСервереБезКонтекста
Процедура ЗаменаПериода(Знач Период = Неопределено) Экспорт

    Запрос = Новый Запрос;
    Запрос.Текст = "ВЫБРАТЬ
        |   ОтветственныеЛицаОрганизации.Период,
        |   ОтветственныеЛицаОрганизации.СтруктурнаяЕдиница,
        |   ОтветственныеЛицаОрганизации.ОтветственноеЛицо
        |ИЗ
        |   РегистрСведений.ОтветственныеЛицаОрганизации КАК ОтветственныеЛицаОрганизации
        |ГДЕ
        |   ОтветственныеЛицаОрганизации.Период <= ДАТАВРЕМЯ(2005, 1, 1)
        |   И ОтветственныеЛицаОрганизации.СтруктурнаяЕдиница.Наименование | ПОДОБНО ""Групп-Трейдинг""
        |   И (ОтветственныеЛицаОрганизации.Должность.Наименование ЕСТЬ NULL
        |       ИЛИ НЕ(ОтветственныеЛицаОрганизации.Должность.Наименование | ПОДОБНО ""Продавец""
        |           ИЛИ ОтветственныеЛицаОрганизации.Должность.Наименование | ПОДОБНО ""Кладовщик""))";

    Результат = Запрос.Выполнить();
    Выборка = Результат.Выбрать();

    Запись = РегистрыСведений.ОтветственныеЛицаОрганизации.СоздатьМенеджерЗаписи();

    Пока Выборка.Следующий() Цикл
        Запись.Период = Выборка.Период;
        Запись.СтруктурнаяЕдиница = Выборка.СтруктурнаяЕдиница;
        Запись.ОтветственноеЛицо = Выборка.ОтветственноеЛицо;

        Запись.Прочитать();

        Если Запись.Выбран() Тогда
            Запись.Период = ?(Период = Неопределено, '20040101', Период);
            Запись.Записать()
        КонецЕсли;
    КонецЦикла;

КонецПроцедуры

#КонецЕсли

#Если Сервер Тогда

а = 1;
б = 2;

#КонецЕсли