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

package de.sormuras.bach.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProjectTests {

  @Nested
  class Project99 {

    final Project project = Projects.newProject("project", "99");

    @Test
    void name() {
      assertEquals("project", project.name());
    }

    @Test
    void version() {
      assertEquals("99", project.version().toString());
    }

    @Test
    void toNameAndVersion() {
      assertEquals("project 99", project.toNameAndVersion());
    }

    @Test
    void toJarName() {
      var unit = Projects.unit("unit", "");
      assertEquals("unit-99.jar", project.toJarName(unit, ""));
      assertEquals("unit-99-classifier.jar", project.toJarName(unit, "classifier"));
    }

    @Test
    void toModularJar() {
      var realm = Projects.realm("");
      var unit = Projects.unit("unit", "");
      var actual = project.toModularJar(realm, unit);
      assertEquals(Path.of(".bach/modules", "unit-99.jar"), actual);
    }

    @Test
    void paths() {
      var realm = new Realm("realm", 0, List.of(), List.of());
      var paths = project.paths();
      assertEquals(Path.of(""), paths.base());
      assertEquals(Path.of(".bach"), paths.out());
      assertEquals(Path.of("lib"), paths.lib());
      assertEquals(Path.of(".bach/first/more"), paths.out("first", "more"));
      assertEquals(Path.of(".bach/classes/realm"), paths.classes(realm));
      assertEquals(Path.of(".bach/modules/realm"), paths.modules(realm));
      assertEquals(Path.of(".bach/sources/realm"), paths.sources(realm));
      assertEquals(Path.of(".bach/documentation/javadoc"), paths.javadoc());
    }

    @Test
    void units() {
      assertTrue(project.structure().units().isEmpty());
    }

    @Test
    void realms() {
      assertTrue(project.structure().realms().isEmpty());
    }

    @Test
    void tuner() {
      var tuner = project.tuner();
      assertNotNull(tuner);
      assertSame(Tuner.class, tuner.getClass());
    }

    @Test
    void library() {
      var library = project.library();
      assertNotNull(library);
      assertTrue(library.requires().isEmpty());
      assertTrue(library.map().isEmpty());
    }
  }
}