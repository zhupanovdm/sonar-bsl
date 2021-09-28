package org.zhupanovdm.bsl.sonar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.RecognitionException;
import lombok.Data;
import org.sonar.api.batch.fs.InputFile;
import org.zhupanovdm.bsl.BslParser;
import org.zhupanovdm.bsl.tree.BslTreeCreator;
import org.zhupanovdm.bsl.tree.module.Module;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;

@Data
public class BslModuleContext {
    private final InputFile file;
    private final Charset charset;
    private final String contents;
    private final Module entry;
    private Exception exception;

    public BslModuleContext(InputFile file) {
        this.file = file;
        this.charset = file.charset();
        this.contents = readFile(file);
        this.entry = createTree(parseContents(this.contents));
    }

    private String readFile(@Nullable InputFile inputFile) {
        if (inputFile == null) {
            return null;
        }
        try {
            return inputFile.contents();
        } catch (IOException exception) {
            this.exception = exception;
            return null;
        }
    }

    private AstNode parseContents(@Nullable String fileContents) {
        if (fileContents == null) {
            return null;
        }
        try {
            return BslParser.create(charset).parse(fileContents);
        } catch (RecognitionException e) {
            this.exception = new IllegalStateException("Cannot parse " + file, e);
            return null;
        }
    }

    private Module createTree(@Nullable AstNode root) {
        if (root == null) {
            return null;
        }
        return new BslTreeCreator().create(root);
    }
}
