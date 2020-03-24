package de.sormuras.bach.api;

import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/** A module source description unit. */
public /*static*/ final class Unit {

  /** Return all source paths of the given units. */
  public static Stream<Path> paths(Collection<Unit> units) {
    return units.stream().flatMap(unit -> unit.sources(Source::path));
  }

  private final Path info;
  private final ModuleDescriptor descriptor;
  private final List<Source> sources;
  private final List<Path> resources;

  public Unit(Path info, ModuleDescriptor descriptor, List<Source> sources, List<Path> resources) {
    this.info = Objects.requireNonNull(info, "info");
    this.descriptor = Objects.requireNonNull(descriptor, "descriptor");
    this.sources = List.copyOf(sources);
    this.resources = List.copyOf(resources);
  }

  public Path info() {
    return info;
  }

  public ModuleDescriptor descriptor() {
    return descriptor;
  }

  public List<Source> sources() {
    return sources;
  }

  public List<Path> resources() {
    return resources;
  }

  public String name() {
    return descriptor.name();
  }

  public boolean isMainClassPresent() {
    return descriptor.mainClass().isPresent();
  }

  public <T> Stream<T> sources(Function<Source, T> mapper) {
    if (sources.isEmpty()) return Stream.empty();
    if (sources.size() == 1) return Stream.of(mapper.apply(sources.get(0)));
    return sources.stream().map(mapper);
  }

  public boolean isMultiRelease() {
    if (sources.isEmpty()) return false;
    if (sources.size() == 1) return sources.get(0).isTargeted();
    return sources.stream().allMatch(Source::isTargeted);
  }
}
