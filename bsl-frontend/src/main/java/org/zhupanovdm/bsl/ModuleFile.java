package org.zhupanovdm.bsl;

import org.zhupanovdm.bsl.tree.module.Module;

import java.nio.charset.Charset;
import java.util.List;

public interface ModuleFile {
    Charset getCharset();
    String getContents();
    Module getEntry();

    List<Issue> getIssues();
    void addIssue(Issue issue);
}
