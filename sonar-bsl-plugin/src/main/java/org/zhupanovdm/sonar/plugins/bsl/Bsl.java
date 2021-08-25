package org.zhupanovdm.sonar.plugins.bsl;

import org.sonar.api.resources.AbstractLanguage;

public class Bsl extends AbstractLanguage {
    public static final String NAME = "BSL";
    public static final String KEY = "bsl";

    public static final String FILE_SUFFIX = "bsl";

    public Bsl() {
        super(KEY, NAME);
    }

    @Override
    public String[] getFileSuffixes() {
        return new String[] {FILE_SUFFIX};
    }

}
