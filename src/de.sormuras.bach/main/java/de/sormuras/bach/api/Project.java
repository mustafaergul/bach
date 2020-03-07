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

import java.lang.module.ModuleDescriptor.Version;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/** Bach's project model. */
public /*static*/ final class Project {

  /** Return a mutable builder for creating a project instance. */
  public static Builder builder() {
    return new Builder();
  }

  private final String name;
  private final Version version;
  private final Structure structure;
  private final Library library;

  private Project(String name, Version version, Structure structure, Library library) {
    this.name = Objects.requireNonNull(name, "name");
    this.version = version;
    this.structure = Objects.requireNonNull(structure, "structure");
    this.library = Objects.requireNonNull(library, "library");
  }

  public String name() {
    return name;
  }

  public Version version() {
    return version;
  }

  public Structure structure() {
    return structure;
  }

  public Library library() {
    return library;
  }

  public Paths paths() {
    return structure().paths();
  }

  public Tuner tuner() {
    return structure().tuner();
  }

  public String toNameAndVersion() {
    if (version == null) return name;
    return name + ' ' + version;
  }

  /** Compose JAR file name by preferring passed argument values over project properties. */
  public String toJarName(Unit unit, String classifier) {
    var unitVersion = unit.descriptor().version();
    var version = unitVersion.isPresent() ? unitVersion : Optional.ofNullable(this.version);
    var versionSuffix = version.map(v -> "-" + v).orElse("");
    var classifierSuffix = classifier.isEmpty() ? "" : "-" + classifier;
    return unit.name() + versionSuffix + classifierSuffix + ".jar";
  }

  /** Compose path to the Java module specified by its realm and modular unit. */
  public Path toModularJar(Realm realm, Unit unit) {
    return paths().modules(realm).resolve(toJarName(unit, ""));
  }

  /** A mutable builder for a {@link Project}. */
  public static class Builder {

    private String name;
    private Version version;

    private Paths paths;
    private List<Unit> units;
    private List<Realm> realms;
    private Tuner tuner;

    private Set<String> libraryRequires;
    private Map<String, URI> libraryMap;

    private Builder() {
      name(null);
      version((Version) null);
      base("");
      units(List.of());
      realms(List.of());
      tuner(new Tuner());
      requires(Set.of());
      map(Map.of());
    }

    public Project build() {
      var structure = new Structure(paths, units, realms, tuner);
      var library = new Library(new TreeSet<>(libraryRequires), new TreeMap<>(libraryMap));
      return new Project(name, version, structure, library);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder version(Version version) {
      this.version = version;
      return this;
    }

    public Builder version(String version) {
      return version(Version.parse(version));
    }

    public Builder paths(Paths paths) {
      this.paths = paths;
      return this;
    }

    public Builder base(Path base) {
      return paths(Paths.of(base));
    }

    public Builder base(String base) {
      return base(Path.of(base));
    }

    public Builder units(List<Unit> units) {
      this.units = units;
      return this;
    }

    public Builder realms(List<Realm> realms) {
      this.realms = realms;
      return this;
    }

    public Builder tuner(Tuner tuner) {
      this.tuner = tuner;
      return this;
    }

    public Builder requires(Set<String> libraryRequires) {
      this.libraryRequires = libraryRequires;
      return this;
    }

    public Builder map(Map<String, URI> libraryMap) {
      this.libraryMap = libraryMap;
      return this;
    }
  }
}