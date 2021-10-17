package org.zhupanovdm.bsl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zhupanovdm.bsl.context.ModuleKind;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.BslTreePublisher;
import org.zhupanovdm.bsl.tree.BslTreeSubscribers;
import org.zhupanovdm.bsl.tree.module.ModuleRoot;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
public abstract class AbstractModuleContext {
    protected ModuleFile file;
    protected ModuleRoot entry;
    protected ModuleKind kind;

    public AbstractModuleContext(@Nonnull ModuleFile file) {
        this.file = file;
        this.entry = parse(file);
    }

    public void setFile(ModuleFile file) {
        this.file = file;
        this.entry = parse(file);
    }

    public abstract void addIssue(@Nonnull Issue issue);

    public void scan(BslTreeSubscribers subscribers) {
        new BslTreePublisher(subscribers).scan(this);
    }

    private static ModuleRoot parse(ModuleFile file) {
        return BslTreeCreator.module(BslParser.create(file.getCharset()).parse(file.getContent()));
    }
}
