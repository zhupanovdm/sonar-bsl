package org.zhupanovdm.bsl.context;

import org.zhupanovdm.bsl.AbstractModuleContext;
import org.zhupanovdm.bsl.tree.BslTreeSubscriber;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModuleFileDescriber implements BslTreeSubscriber {
    private AbstractModuleContext context;
    private URI uri;
    private Path path;

    @Override
    public void onEnterFile(AbstractModuleContext context) {
        this.context = context;
        this.uri = context.getFile().getUri();
        this.path = Paths.get(this.uri);
        describe();
    }

    private void describe() {
        int count = path.getNameCount();
        Path fileName = path.getName(count - 1);
        Path catalog1Name = path.getName(count - 2);
        if (count > 2) {
            Path catalog2Name = path.getName(count - 3);
            Path catalog3Name = path.getName(count - 4);
        }
    }
}
