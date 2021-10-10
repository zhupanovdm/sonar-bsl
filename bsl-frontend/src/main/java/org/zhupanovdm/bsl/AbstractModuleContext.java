package org.zhupanovdm.bsl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.symbol.SymbolTable;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
public abstract class AbstractModuleContext {
    protected ModuleFile file;
    protected ModuleRoot entry;
    protected SymbolTable symbolTable;

    public AbstractModuleContext(@Nonnull ModuleFile file) {
        setFile(file);
    }

    public void setFile(ModuleFile file) {
        this.file = file;
        this.entry = BslTreeCreator.module(BslParser.create(file.getCharset()).parse(file.getContent()));
    }

    public abstract void addIssue(@Nonnull Issue issue);
}
