package com.github.sormuras.bach.tool;

import com.github.sormuras.bach.Command;
import java.util.List;

public record Jar(List<Argument> arguments) implements Command<Jar> {

  public Jar() {
    this(List.of());
  }

  @Override
  public String name() {
    return "jar";
  }

  @Override
  public Jar arguments(List<Argument> arguments) {
    return new Jar(arguments);
  }
}