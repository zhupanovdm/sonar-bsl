package org.zhupanovdm.bsl.context;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum ModuleKind {
    ManagedApplication("ManagedApplicationModule"),
    OrdinaryApplication("OrdinaryApplicationModule"),
    ExternalConnection("ExternalConnectionModule"),
    Session("SessionModule"),
    Common("Module"),

    Object("ObjectModule"),
    RecordSet("RecordSetModule"),
    Manager("ManagerModule"),
    Form("Module"),
    Command("CommandModule");

    @Getter
    private final String name;

    ModuleKind(String name) {
        this.name = name;
    }

    public static Set<ModuleKind> application() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ManagedApplication, OrdinaryApplication, ExternalConnection, Session)));
    }

    public static Set<ModuleKind> register() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(RecordSet, Manager)));
    }

    public static Set<ModuleKind> object() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Object, Manager)));
    }
}
