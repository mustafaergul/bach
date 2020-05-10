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

import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;

class BuildJigsawQuickStartWithBach {

  public static void main(String... args) {
    Bach.of(
            new Bach.Project.Builder()
                .base(Bach.Project.Base.of(Path.of("doc", "project", "JigsawQuickStart")))
                .title("Module System Quick-Start Guide Greetings")
                .version(ModuleDescriptor.Version.parse("47.11"))
                .walk()
                .build())
        .build()
        .assertSuccessful();
  }
}
