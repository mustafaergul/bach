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

package de.sormuras.bach.project;

import de.sormuras.bach.util.Modules;
import de.sormuras.bach.util.Paths;
import java.lang.module.ModuleDescriptor.Version;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import javax.lang.model.SourceVersion;

public class ProjectBuilder {

  /** Create project instance auto-configured by scanning the current working directory. */
  public static Project build(Path base) {
    return build(Folder.of(base));
  }

  public static Project build(Folder folder) {
    // TODO Read default values from `.bach/project.properties`
    var properties = new Properties();
    return build(folder, properties);
  }

  public static Project build(Folder folder, Properties properties) {
    var name =
        properties.getProperty(
            "name",
            Optional.ofNullable(folder.base().toAbsolutePath().getFileName())
                .map(Path::toString)
                .orElse("project"));
    var version = properties.getProperty("version", "0");
    return new Project(name, Version.parse(version), structure(folder), null);
  }

  public static Structure structure(Folder folder) {
    if (!Files.isDirectory(folder.base())) {
      throw new IllegalArgumentException("Not a directory: " + folder.base());
    }
    var main =
        new Realm(
            "main",
            Set.of(Realm.Modifier.DEPLOY),
            List.of(folder.src("{MODULE}/main/java")),
            List.of(folder.lib()));
    var test =
        new Realm(
            "test",
            Set.of(Realm.Modifier.TEST),
            List.of(folder.src("{MODULE}/test/java"), folder.src("{MODULE}/test/module")),
            List.of(folder.modules("main"), folder.lib()));
    var realms = List.of(main, test);

    var modules = new TreeMap<String, List<String>>(); // local realm-based module registry
    var units = new ArrayList<Unit>();
    for (var root : Paths.list(folder.src(), Files::isDirectory)) {
      var module = root.getFileName().toString();
      if (!SourceVersion.isName(module.replace(".", ""))) continue;
      realm:
      for (var realm : realms) {
        modules.putIfAbsent(realm.name(), new ArrayList<>());
        for (var zone : List.of("java", "module")) {
          var info = root.resolve(realm.name()).resolve(zone).resolve("module-info.java");
          if (Files.isRegularFile(info)) {
            var sources = new ArrayList<Source>();
            for (var source : realm.sourcePaths()) {
              var path = Path.of(source.toString().replace("{MODULE}", module));
              sources.add(Source.of(path));
            }
            var patches = new ArrayList<Path>();
            if (realm.name().equals("test") && modules.get("main").contains(module)) {
              patches.add(folder.src().resolve(module).resolve("main/java"));
            }
            var descriptor = Modules.describe(Paths.readString(info));
            units.add(new Unit(realm, info, descriptor, sources, patches));
            modules.get(realm.name()).add(module);
            continue realm; // first zone hit wins
          }
        }
      }
    }

    return new Structure(folder, Library.of(), realms, units);
  }
}
