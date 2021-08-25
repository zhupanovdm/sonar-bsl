package org.zhupanovdm.bsl.metrics;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;
import org.zhupanovdm.bsl.parser.BslParser;
import org.zhupanovdm.bsl.TestBslParser;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.fest.assertions.Assertions.assertThat;

public class ModuleMetricsTest {

    @Test
    public void statements() {
        ModuleMetrics metrics = new ModuleMetrics(TestBslParser.parse("Statements.bsl"));
        assertThat(metrics.getNumberOfStatements()).isEqualTo(18);
    }

    @Test
    public void emptyStatements() {
        AstNode tree = BslParser.create(UTF_8).parse(";; a = 5;;; b();;");
        assertThat(new ModuleMetrics(tree).getNumberOfStatements()).isEqualTo(8);
    }

    @Test
    public void functions() {
        ModuleMetrics metrics = new ModuleMetrics(TestBslParser.parse("Functions.bsl"));
        assertThat(metrics.getNumberOfFunctions()).isEqualTo(4);
    }

    @Test
    public void executableLines() {
        ModuleMetrics metrics = new ModuleMetrics(TestBslParser.parse("Statements.bsl"));
        assertThat(metrics.getExecutableLines())
                .isEqualTo("2=1;4=1;6=1;7=1;8=1;12=1;13=1;16=1;17=1;20=1;21=1;23=1;26=1;27=1;29=1;31=1;33=1;");
    }

}
