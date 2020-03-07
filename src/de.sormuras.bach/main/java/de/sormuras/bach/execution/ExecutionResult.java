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

package de.sormuras.bach.execution;

import java.time.Duration;

/** Task execution result record returned by {@link Task#execute(ExecutionContext)}. */
public /*static*/ final class ExecutionResult {
  private final int code;
  private final Duration duration;
  private final String out;
  private final String err;
  private final Throwable throwable;

  public ExecutionResult(int code, Duration duration, String out, String err, Throwable throwable) {
    this.code = code;
    this.duration = duration;
    this.out = out;
    this.err = err;
    this.throwable = throwable;
  }

  public int code() {
    return code;
  }

  public Duration duration() {
    return duration;
  }

  public String out() {
    return out;
  }

  public String err() {
    return err;
  }

  public Throwable throwable() {
    return throwable;
  }
}