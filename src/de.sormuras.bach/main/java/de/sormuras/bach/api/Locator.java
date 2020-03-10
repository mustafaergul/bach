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

import de.sormuras.bach.api.locator.CoordinatesLocator;
import de.sormuras.bach.api.locator.DirectLocator;
import de.sormuras.bach.api.locator.DynamicLocator;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

/** Module name to {@link URL} instance function. */
@FunctionalInterface
public interface Locator {

  /** Compute an optional URI for the given module name. */
  Optional<URL> locate(String module);

  static Locator direct(Map<String, URL> urls) {
    return new DirectLocator(urls);
  }

  static Locator dynamicCentral(Map<String, String> variants) {
    return dynamicRepository(Maven.CENTRAL_REPOSITORY, variants);
  }

  static Locator dynamicRepository(String repository, Map<String, String> variants) {
    return new DynamicLocator(repository, variants);
  }

  static Locator mavenCentral(Map<String, String> coordinates) {
    return mavenRepository(Maven.CENTRAL_REPOSITORY, coordinates);
  }

  static Locator mavenRepository(String repository, Map<String, String> coordinates) {
    return new CoordinatesLocator(repository, coordinates);
  }
}