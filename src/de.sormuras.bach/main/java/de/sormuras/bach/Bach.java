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

package de.sormuras.bach;

import de.sormuras.bach.project.Folder;
import de.sormuras.bach.project.Project;
import de.sormuras.bach.project.Structure;
import java.lang.module.ModuleDescriptor.Version;
import java.util.List;

/** Build modular Java project. */
public class Bach {

  /** Bach.java's version. */
  public static final String VERSION = "2.0-ea";

  /** Main entry-point. */
  public static void main(String... args) {
    var structure = new Structure(Folder.of(), List.of(), List.of());
    var project = new Project("zero", Version.parse("0"), structure);
    // TODO new Bach(Log.ofSystem(), project).execute(Task.build());
    System.err.println("TODO: Execute build task for auto-configured project: " + project);
  }

  private final Log log;
  private final Project project;

  public Bach(Log log, Project project) {
    this.log = log;
    this.project = project;
    log.debug("Bach.java %s initialized.", VERSION);
  }

  public Log getLog() {
    return log;
  }

  public Project getProject() {
    return project;
  }

  public void execute(Task... tasks) {
    try {
      for (var task : tasks) {
        log.debug("Executing task: %s", task.getClass().getSimpleName());
        task.execute(this);
      }
    } catch (Exception e) {
      throw new Error("Task failed to execute: " + e, e);
    }
  }
}
