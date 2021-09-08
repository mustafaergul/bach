import com.github.sormuras.bach.Bach;
import com.github.sormuras.bach.Command;
import com.github.sormuras.bach.ToolCall;
import com.github.sormuras.bach.conventional.ConventionalSpace;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;

class build {
  public static void main(String... args) {
    switch (System.getProperty("build", "default")) {
      default -> BuildWithBachApi.main(args);
      case "conventional" -> BuildWithConventionalApi.main(args);
      case "project" -> BuildWithProjectApi.main(args);
    }
  }

  static class BuildWithBachApi {
    public static void main(String... args) {
      var classes = Path.of(".bach/workspace/classes");
      var modules = Path.of(".bach/workspace/modules");
      try (var bach = new Bach(args)) {
        var options = bach.configuration().projectOptions();
        var version = options.version().map(Object::toString).orElse("99");
        bach.run(
            Command.javac()
                .modules("com.greetings")
                .moduleSourcePathPatterns(".")
                .add("--module-version", version)
                .add("-d", classes));
        bach.run(ToolCall.of("directories", "clean", modules));
        bach.run(
            Command.jar()
                .mode("--create")
                .file(modules.resolve("com.greetings@" + version + ".jar"))
                .main("com.greetings.Main")
                .filesAdd(classes.resolve("com.greetings")));
        bach.run(ToolCall.module(ModuleFinder.of(modules), "com.greetings"))
            .visit(bach.logbook()::print);
      }
    }
  }

  static class BuildWithConventionalApi {
    public static void main(String... args) {
      try (var bach = new Bach(args)) {
        var space =
            ConventionalSpace.of(bach)
                .modulesAddModule("com.greetings", module -> module.main("com.greetings.Main"));
        space.compile();
        space.runModule("com.greetings", run -> run.add("fun"));
      }
    }
  }

  static class BuildWithProjectApi {
    public static void main(String... args) {
      throw new UnsupportedOperationException("BuildWithProjectApi");
    }
  }
}
