/*
 * Bach - Java Shell Builder
 * Copyright (C) 2019 Christian Stein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.sormuras.bach;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/*BODY*/
/** Java Shell Builder. */
public class Bach {

  /** Version of Bach, {@link Runtime.Version#parse(String)}-compatible. */
  public static final String VERSION = "2-ea";

  /** Convenient short-cut to {@code "user.home"} as a path. */
  static final Path USER_HOME = Path.of(System.getProperty("user.home"));

  /** Convenient short-cut to {@code "user.dir"} as a path. */
  static final Path USER_PATH = Path.of(System.getProperty("user.dir"));

  /** Main entry-point making use of {@link System#exit(int)} on error. */
  public static void main(String... arguments) {
    var bach = new Bach();
    var args = List.of(arguments);
    var code = bach.main(args);
    if (code != 0) {
      System.err.printf("Bach main(%s) failed with error code: %d%n", args, code);
      System.exit(code);
    }
  }

  final Run run;
  final Project project;

  public Bach() {
    this(Run.system());
  }

  public Bach(Run run) {
    this(run, Project.of(run.home));
  }

  public Bach(Run run, Project project) {
    this.run = run;
    this.project = project;
    run.log(DEBUG, "%s initialized for %s", this, project);
    project.toStrings(line -> run.log(DEBUG, "  %s", line));
    run.log(TRACE, "Run instance properties");
    run.log(TRACE, "  class = %s", run.getClass().getSimpleName());
    run.toStrings(line -> run.log(TRACE, "  %s", line));
  }

  void help() {
    run.log(TRACE, "Bach::help()");
    run.out.println("Usage of Bach.java (" + VERSION + "):  java Bach.java [<task>...]");
    run.out.println("Available default tasks are:");
    for (var task : Task.Default.values()) {
      var name = task.name().toLowerCase();
      var text =
          String.format(" %-9s    ", name) + String.join('\n' + " ".repeat(14), task.description);
      text.lines().forEach(run.out::println);
    }
    run.out.println("Project information");
    project.toStrings(run.out::println);
  }

  /** Main entry-point, by convention, a zero status code indicates normal termination. */
  int main(List<String> arguments) {
    run.log(TRACE, "Bach::main(%s)", arguments);
    List<Task> tasks;
    try {
      tasks = Task.of(arguments);
      run.log(DEBUG, "tasks = " + tasks);
    } catch (IllegalArgumentException e) {
      run.log(ERROR, "Converting arguments to tasks failed: " + e);
      return 1;
    }
    if (run.dryRun) {
      run.log(INFO, "Dry-run ends here.");
      return 0;
    }
    run(tasks);
    return 0;
  }

  /** Execute a collection of tasks sequentially on this instance. */
  int run(Collection<? extends Task> tasks) {
    run.log(TRACE, "Bach::run(%s)", tasks);
    run.log(DEBUG, "Performing %d task(s)...", tasks.size());
    for (var task : tasks) {
      try {
        run.log(TRACE, ">> %s", task);
        task.perform(this);
        run.log(TRACE, "<< %s", task);
      } catch (Exception exception) {
        run.log(ERROR, "Task %s threw: %s", task, exception);
        if (run.debug) {
          exception.printStackTrace(run.err);
        }
        return 1;
      }
    }
    run.log(DEBUG, "%s task(s) successfully performed.", tasks.size());
    return 0;
  }

  /** Resolve required external assets, like 3rd-party modules. */
  void synchronize() throws Exception {
    run.log(TRACE, "Bach::synchronize()");
    synchronizeModuleUriProperties(run.home.resolve(project.path(Project.Property.PATH_LIB)));
    // TODO synchronizeMissingLibrariesByParsingModuleDescriptors();
  }

  private void synchronizeModuleUriProperties(Path root) throws Exception {
    run.log(DEBUG, "Synchronizing 3rd-party module uris below %s", root);
    if (Files.notExists(root)) {
      run.log(DEBUG, "Directory %s doesn't exist, not synchronizing.", root);
      return;
    }
    var paths = new ArrayList<Path>();
    try (var stream = Files.walk(root)) {
      stream
          .filter(path -> path.getFileName().toString().equals("module-uri.properties"))
          .forEach(paths::add);
    }
    var synced = new ArrayList<Path>();
    for (var path : paths) {
      var directory = path.getParent();
      var downloader = new Downloader(run, directory);
      var properties = new Properties();
      try (var stream = Files.newInputStream(path)) {
        properties.load(stream);
      }
      if (properties.isEmpty()) {
        run.log(DEBUG, "No module uri declared in %s", path.toUri());
        continue;
      }
      run.log(DEBUG, "Syncing %d module uri(s) to %s", properties.size(), directory.toUri());
      for (var value : properties.values()) {
        var string = run.replaceVariables(value.toString());
        var uri = URI.create(string);
        uri = uri.isAbsolute() ? uri : run.home.resolve(string).toUri();
        run.log(DEBUG, "Syncing %s", uri);
        var target = downloader.download(uri);
        synced.add(target);
        run.log(DEBUG, " o %s", target.toUri());
      }
    }
    run.log(DEBUG, "Synchronized %d module uri(s).", synced.size());
  }

  @Override
  public String toString() {
    return "Bach (" + VERSION + ")";
  }
}
