package test.projects;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.sormuras.bach.Command;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MultiRelease9Tests {

  @Test
  void build(@TempDir Path temp) throws Exception {
    var cli = new CLI("MultiRelease-9", temp);
    var out =
        cli.start(
            Command.of("bach")
                .add("--verbose")
                .add("--project-targets-java", "9")
                .add("--limit-tools", "javac,jar")
                .add("--jar-with-sources")
                .add("build"));
    assertLinesMatch(
        """
        >> BACH'S INITIALIZATION >>
        Perform main action: `build`
        Build MultiRelease-9 0
        >> INFO + BUILD >>
        Build took .+
        Logbook written to .+
        """
            .lines(),
        out.lines());
    var foo = cli.workspace("modules", "foo@0.jar");
    assertTrue(Files.exists(foo));
    assertLinesMatch(
        """
        META-INF/
        META-INF/MANIFEST.MF
        module-info.class
        foo/
        foo/Foo.class
        foo/Foo.java
        module-info.java
        foo/Foo.txt
        META-INF/versions/11/
        META-INF/versions/11/foo/
        META-INF/versions/11/foo/Foo.class
        META-INF/versions/11/foo/Foo.java
        META-INF/versions/11/foo/Foo.txt
        META-INF/versions/13/
        META-INF/versions/13/foo/
        META-INF/versions/13/foo/Foo.txt
        META-INF/versions/15/
        META-INF/versions/15/foo/
        META-INF/versions/15/foo/Foo.class
        META-INF/versions/15/foo/Foo.java
        """
            .lines()
            .sorted(),
        CLI.run(Command.jar().add("--list").add("--file", foo)).lines().sorted());
  }
}
