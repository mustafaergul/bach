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

var boot = Path.of(".bach/lib/bach-boot.jsh")
if (Files.notExists(boot)) {
  var version = System.getProperty("version", "HEAD");
  var uri = "https://github.com/sormuras/bach/raw/" + version + "/src/bach/bach-boot.jsh";
  Files.createDirectories(boot.getParent());
  try (var stream = new URL(uri).openStream()) { Files.copy(stream, boot); }
}

/open .bach/lib/bach-boot.jsh

Bach.main("help")

/exit
