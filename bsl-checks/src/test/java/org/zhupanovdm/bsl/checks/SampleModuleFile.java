package org.zhupanovdm.bsl.checks;

import org.sonarsource.analyzer.commons.checks.verifier.FileContent;
import org.zhupanovdm.bsl.ModuleFile;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;

import static org.zhupanovdm.bsl.checks.CheckTestUtils.resource;

class SampleModuleFile implements ModuleFile {
    private final FileContent sample;

    public SampleModuleFile(String fileName) {
        this.sample = resource(fileName);
    }

    public Path getPath() {
        return sample.getPath();
    }

    @Override
    public Charset getCharset() {
        return Charset.defaultCharset();
    }

    @Override
    public String getContent() {
        return sample.getContent();
    }

    @Override
    public URI getUri() {
        return getPath().toUri();
    }

    @Override
    public String getName() {
        return sample.getName();
    }
}
