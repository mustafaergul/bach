package test.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import com.github.sormuras.bach.Bach;
import com.github.sormuras.bach.Logbook;
import com.github.sormuras.bach.Options;
import com.github.sormuras.bach.api.CodeSpace;
import com.github.sormuras.bach.tool.Jar;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class SimpleTests {

  @Test
  void build() {
    var name = "Simple";
    var root = Path.of("test.projects", name);
    var bach =
        Bach.of(
            Logbook.ofErrorPrinter(),
            Options.ofCommandLineArguments(
                """
                --chroot
                  %s
                --verbose
                --limit-tools
                  javac,jar
                --main-jar-with-sources
                build
                """
                    .formatted(root)));

    assertEquals(0, bach.run(), bach.logbook().toString());

    assertLinesMatch(
        """
        >> BACH'S INITIALIZATION >>
        Simple 1.0.1
        >> INFO + BUILD >>
        Bach run took .+
        Logbook written to .+
        """
            .lines(),
        bach.logbook().lines());

    var jar = bach.project().folders().modules(CodeSpace.MAIN, "simple@1.0.1.jar");
    assertLinesMatch(
        """
        META-INF/
        META-INF/MANIFEST.MF
        README.md
        module-info.class
        module-info.java
        simple/
        simple/Main.class
        simple/Main.java
        simple/Main.txt
        simple/internal/
        simple/internal/Interface.class
        simple/internal/Interface.java
        """
            .lines()
            .sorted(),
        bach.run(new Jar().with("--list").with("--file", jar)).output().lines().sorted());
  }
}