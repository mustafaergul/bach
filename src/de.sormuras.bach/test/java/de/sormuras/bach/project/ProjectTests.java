/*
 * Bach - Java Shell Builder
 * Copyright (C) 2020 Christian Stein
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class ProjectTests {

  @Test
  void touch() {
    var project =
        Project.of("touch")
            .with(Basics.of("touch", "11.3"))
            .with(
                Structure.of()
                    .with(silk("master-SNAPSHOT"))
                    .with(new JUnitPlatformLocators())
                    .with(new JUnitJupiterLocators())
                    .with(new JUnitVintageLocators()));

    var basics = project.basics();
    assertEquals("11.3", basics.version().toString());

    var silk = project.findModuleUri("se.jbee.inject").orElseThrow();
    assertEquals("https", silk.getScheme());
    assertEquals("jitpack.io", silk.getHost());
    assertEquals("silk-master-SNAPSHOT.jar", Path.of(silk.getPath()).getFileName().toString());

    var junit4 = project.findModuleUri("junit").orElseThrow();
    assertTrue(junit4.toString().endsWith("4.13.jar"), junit4 + " ends with `4.13.jar`");
  }

  private List<Locator> silk(String version) {
    return List.of(Locator.of("se.jbee.inject").fromJitPack("jbee", "silk", version));
  }

  static class JUnitPlatformLocators extends TreeSet<Locator> {

    final String version = "1.7.0-M1";

    JUnitPlatformLocators() {
      var suffixes =
          Set.of(".commons", ".console", ".engine", ".launcher", ".reporting", ".testkit");
      for (var suffix : suffixes) add(locator(suffix));
    }

    private Locator locator(String suffix) {
      var module = "org.junit.platform" + suffix;
      var artifact = "junit-platform" + suffix.replace('.', '-');
      return Locator.of(module).fromMavenCentral("org.junit.platform", artifact, version);
    }
  }

  static class JUnitJupiterLocators extends TreeSet<Locator> {

    final String version = "5.7.0-M1";

    JUnitJupiterLocators() {
      var suffixes = Set.of("", ".api", ".engine", ".params");
      for (var suffix : suffixes) add(locator(suffix));
    }

    private Locator locator(String suffix) {
      var module = "org.junit.jupiter" + suffix;
      var artifact = "junit-jupiter" + suffix.replace('.', '-');
      return Locator.of(module).fromMavenCentral("org.junit.jupiter", artifact, version);
    }
  }

  static class JUnitVintageLocators extends TreeSet<Locator> {
    {
      add(Locator.of("junit").fromMavenCentral("junit", "junit", "4.13"));
      add(Locator.of("org.hamcrest").fromMavenCentral("org.hamcrest", "hamcrest", "2.2"));
      add(
          Locator.of("org.junit.vintage.engine")
              .fromMavenCentral("org.junit.vintage", "junit-vintage-engine", "5.7.0-M1"));
    }
  }
}
