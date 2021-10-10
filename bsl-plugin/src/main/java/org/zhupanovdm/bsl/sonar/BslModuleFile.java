package org.zhupanovdm.bsl.sonar;

import lombok.Data;
import org.sonar.api.batch.fs.InputFile;
import org.zhupanovdm.bsl.ModuleFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

@Data
public class BslModuleFile implements ModuleFile {
    private final InputFile input;
    private final String contents;

    public BslModuleFile(@Nonnull InputFile input) {
        this.input = input;
        try {
            this.contents = input.contents();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read content of input file " + input, e);
        }
    }

    @Override
    public Charset getCharset() {
        return input.charset();
    }

    @Override
    public String getContent() {
        return contents;
    }

    @Override
    public URI getUri() {
        return input.uri();
    }

    @Override
    public String getName() {
        return input.filename();
    }
}
