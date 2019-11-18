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

import de.sormuras.bach.util.Paths;
import java.nio.file.Path;
import java.util.List;

public /*record*/ class Realm {
  private final String name;
  private final List<Path> sourcePaths;
  private final List<Path> modulePaths;

  public Realm(String name, List<Path> sourcePaths, List<Path> modulePaths) {
    this.name = name;
    this.sourcePaths = List.copyOf(sourcePaths);
    this.modulePaths = List.copyOf(modulePaths);
  }

  public String name() {
    return name;
  }

  public List<Path> sourcePaths() {
    return sourcePaths;
  }

  public List<Path> modulePaths() {
    return modulePaths;
  }

  public String moduleSourcePath() {
    return Paths.join(sourcePaths).replace("{MODULE}", "*");
  }
}
