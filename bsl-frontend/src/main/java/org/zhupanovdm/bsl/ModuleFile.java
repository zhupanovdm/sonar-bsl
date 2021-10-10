package org.zhupanovdm.bsl;

import java.net.URI;
import java.nio.charset.Charset;

public interface ModuleFile {
    Charset getCharset();
    String getContent();
    URI getUri();
    String getName();
}
