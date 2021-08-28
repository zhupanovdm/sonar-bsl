package org.zhupanovdm.bsl.toolkit.tokenizer;

import org.sonar.channel.CodeReader;
import org.sonar.colorizer.HtmlCodeBuilder;
import org.sonar.colorizer.NotThreadSafeTokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BslDirectiveTokenizer extends NotThreadSafeTokenizer {
    private final String tagBefore;
    private final String tagAfter;
    private final StringBuilder tmpBuilder = new StringBuilder();
    private final Matcher matcher = Pattern.compile("&[\\s&&[^\n\r]]*+[\\p{javaJavaIdentifierStart}&&[^$]]+[\\p{javaJavaIdentifierPart}&&[^$]]*+").matcher("");

    public BslDirectiveTokenizer(String tagBefore, String tagAfter) {
        this.tagBefore = tagBefore;
        this.tagAfter = tagAfter;
    }

    @Override
    public boolean consume(CodeReader code, HtmlCodeBuilder codeBuilder) {
        if (code.popTo(matcher, tmpBuilder) > 0) {
            codeBuilder.appendWithoutTransforming(tagBefore);
            codeBuilder.append(tmpBuilder);
            codeBuilder.appendWithoutTransforming(tagAfter);

            tmpBuilder.delete(0, tmpBuilder.length());
            return true;
        }

        return false;
    }

    @Override
    public NotThreadSafeTokenizer clone() {
        return new BslDirectiveTokenizer(tagBefore, tagAfter);
    }

}
