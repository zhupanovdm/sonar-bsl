package org.zhupanovdm.bsl.context;

import java.util.Collections;
import java.util.Set;

public enum MetadataClass {
    Configuration("Конфигурация", "/", ModuleKind.application()),

    AccountingRegister("РегистрБухгалтерии", "/AccountingRegisters", "AccountingRegisters", "РегистрыБухгалтерии", ModuleKind.register()),
    AccumulationRegister("РегистрНакопления", "/AccumulationRegisters", "AccumulationRegisters", "РегистрыНакопления", ModuleKind.register()),
    BusinessProcess("БизнесПроцесс", "/BusinessProcesses", "BusinessProcesses", "БизнесПроцессы", ModuleKind.object()),
    CalculationRegister("РегистрРасчета", "/CalculationRegisters", "CalculationRegisters", "РегистрыРасчета", ModuleKind.register()),
    Catalog("Справочник","/Catalogs", "Catalogs", "Справочники", ModuleKind.object()),
    ChartOfAccounts("ПланСчетов", "/ChartsOfAccounts", "ChartsOfAccounts", "ПланыСчетов", ModuleKind.object()),
    ChartOfCalculationTypes("ПланВидовРасчетов", "/ChartsOfCalculationTypes","ChartsOfCalculationTypes", "ПланыВидовРасчетов", ModuleKind.object()),
    ChartOfCharacteristicTypes("ПланВидовХарактеристик", "/ChartsOfCharacteristicTypes", "ChartsOfCharacteristicTypes", "ПланыВидовХарактеристик", ModuleKind.object()),
    CommandGroup("ГруппаКоманд", "/ChartsOfCharacteristicTypes"),
    CommonAttribute("ОбщийРеквизит", "/CommonAttributes"),
    CommonCommand("ОбщаяКоманда", "/CommonCommands"),
    CommonForm("ОбщаяФорма", "/CommonForms", "CommonForms", "ОбщиеФормы", Collections.emptySet()), // ???
    CommonModule("ОбщийМодуль", "/CommonModules"),
    Constant("Константа", "/Constants", "Constants", "Константы", Collections.emptySet()), // ???
    DataProcessor("Обработка", "/DataProcessors", "DataProcessors", "Обработки", ModuleKind.object()),
    DefinedType("ОпределяемыйТип", "/DefinedTypes"),
    DocumentJournal("ЖурналДокументов", "/DocumentJournals", "DocumentJournals", "ЖурналыДокументов", Collections.emptySet()), // ???
    Document("Документ", "/Documents", "Documents", "Документы", ModuleKind.object()),
    Enum("Перечисление", "/Enums", "Enums", "Перечисления", Collections.emptySet()), // ???
    EventSubscription("ПодпискаНаСобытие", "/EventSubscriptions"),
    ExchangePlan("ПланОбмена", "/ExchangePlans", "ExchangePlans", "ПланыОбмена", ModuleKind.object()),
    FilterCriteria("КритерийОтбора", "/FilterCriteria"),
    FunctionalOption("ФункциональнаяОпция", "/FunctionalOptions"),
    FunctionalOptionsParameter("ПараиетрФункциональныхОпций", "/FunctionalOptionsParameters"),
    HTTPService("HTTPСервис", "/HTTPServices"), // ???
    InformationRegister("РегистрСведений", "/InformationRegisters", "InformationRegisters", "РегистрыСведений", ModuleKind.register()),
    Language("Язык", "/Languages"),
    Report("Отчет", "/Reports", "Reports", "Отчеты", ModuleKind.object()),
    Role("Роль", "/Roles"),
    ScheduledJob("РегламентноеЗадание", "/ScheduledJobs", "ScheduledJobs", "РегламентныеЗадания", Collections.emptySet()), // ???
    Sequence("Последовательность", "/Sequences", "Sequences", "Последовательности", Collections.emptySet()), // ???
    SessionParameter("ПараметрСеанса", "/SessionParameters"),
    SettingsStorage("ХранилищеНастроек", "/SettingsStorages", "SettingsStorages", "ХранилищаНастроек", Collections.emptySet()), // ???
    StyleItem("ЭлементСтиля", "/StyleItems"),
    Subsystem("Подсистема", "/Subsystems", "Subsystems", "Подсистемы", Collections.emptySet()),
    Task("Задача", "/Tasks", "Tasks", "Задачи", ModuleKind.object()),
    WebService("WebСервис", "/WebServices", null, null, Collections.emptySet()), // ???
    XDTOPackage("XDTOПакет", "/XDTOPackages", "XDTOPackages", "XDTOПакеты", Collections.emptySet()); // ???

    private final String catalog;
    private final String nameAlt;
    private final String alias;
    private final String aliasAlt;
    Set<ModuleKind> hasModules;

    MetadataClass(String nameAlt, String catalog, String alias, String aliasAlt, Set<ModuleKind> hasModules) {
        this.nameAlt = nameAlt;
        this.catalog = catalog;
        this.alias = alias;
        this.aliasAlt = aliasAlt;
        this.hasModules = hasModules;
    }

    MetadataClass(String nameAlt, String catalog, Set<ModuleKind> hasModules) {
        this(nameAlt, catalog, null, null, hasModules);
    }

    MetadataClass(String nameAlt, String catalog) {
        this(nameAlt, catalog, Collections.emptySet());
    }
}
