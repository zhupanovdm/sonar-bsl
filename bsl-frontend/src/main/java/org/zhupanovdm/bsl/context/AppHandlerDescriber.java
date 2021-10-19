package org.zhupanovdm.bsl.context;

import lombok.Data;
import lombok.ToString;
import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.tree.BslTree;
import org.zhupanovdm.bsl.tree.definition.CallableDefinition;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.zhupanovdm.bsl.utils.StringUtils.caseInsensitiveBilingualRegexpString;

@Data
public class AppHandlerDescriber {
    private final Pattern namePattern;
    private final String name;

    private boolean isFunction;
    private Set<ModuleKind> allowedLocations = new HashSet<>();
    private MetadataClass metadataLocation;

    private final List<Parameter> parameters = new LinkedList<>();

    public AppHandlerDescriber(String name, String nameAlt) {
        this.name = name;
        this.namePattern = Pattern.compile(caseInsensitiveBilingualRegexpString(name, nameAlt));
    }

    public boolean matches(CallableDefinition callable, AbstractModuleContext context) {
        BslTree.Type type = isFunction ? BslTree.Type.FUNCTION : BslTree.Type.PROCEDURE;
        if (callable.getType() != type) {
            return false;
        }
        if (context.getKind() != null && !allowedLocations.contains(context.getKind())) {
            return false;
        }
        return namePattern.matcher(callable.getName()).matches();
    }

    @Data
    @ToString(of = "name")
    public static class Parameter {
        private String name;
        private boolean isCancel;
        private boolean isStandardHandlerEnabled;
        private int index;
    }
}
