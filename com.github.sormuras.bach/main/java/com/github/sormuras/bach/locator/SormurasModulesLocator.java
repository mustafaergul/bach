package com.github.sormuras.bach.locator;

import com.github.sormuras.bach.Configuration;
import com.github.sormuras.bach.api.ExternalModuleLocation;
import com.github.sormuras.bach.api.ExternalModuleLocator;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SormurasModulesLocator implements ExternalModuleLocator {

  private final String version;
  private Configuration configuration;
  private /*-*/ Map<String, String> uris;

  public SormurasModulesLocator(String version, Configuration configuration) {
    this.version = version;
    this.configuration = configuration;
    this.uris = null;
  }

  @Override
  public Stability stability() {
    return Stability.STABLE;
  }

  @Override
  public Optional<ExternalModuleLocation> locate(String module) {
    if (uris == null)
      try {
        uris = loadUris();
      } catch (Exception exception) {
        throw new RuntimeException("Load URIs failed: " + exception.getMessage(), exception);
      }
    var uri = uris.get(module);
    if (uri == null) return Optional.empty();
    return Optional.of(new ExternalModuleLocation(module, uri));
  }

  private synchronized Map<String, String> loadUris() throws Exception {
    var dir = configuration.folders().tools("sormuras-modules", version);
    var name = "com.github.sormuras.modules@" + version + ".jar";
    var file = dir.resolve(name);
    if (!Files.exists(file)) {
      var uri = "https://github.com/sormuras/modules/releases/download/" + version + "/" + name;
      Files.createDirectories(dir);
      configuration.httpLoad(uri, file);
    }
    var jar = FileSystems.newFileSystem(file);
    var lines = Files.readAllLines(jar.getPath("com/github/sormuras/modules/modules.properties"));
    var tree = new TreeMap<String, String>();
    for (var line : lines) {
      var split = line.indexOf('=');
      var module = line.substring(0, split);
      var uri = line.substring(split + 1);
      tree.put(module, uri);
    }
    return tree;
  }

  @Override
  public String title() {
    var modules = uris == null ? "<not loaded>" : uris.size() + " modules";
    return String.format("sormuras/modules@%s -> %s", version, modules);
  }
}