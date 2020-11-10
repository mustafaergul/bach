package com.github.sormuras.bach.tool;

import java.util.List;

/** A tool call object with its name and an array of argument strings. */
public interface ToolCall {
  /**
   * Return the name of the tool to be called.
   *
   * @return a non-empty string representing the name of the tool to be called
   */
  String name();

  /**
   * Return the possibly empty list of arguments of this tool call.
   *
   * @return a list of strings
   */
  List<String> args();

  /**
   * Return an immutable tool call instance representing this tool call.
   *
   * @return an instance of command representing this tool call
   */
  default Command toCommand() {
    return (this instanceof Command) ? (Command) this : new Command(name(), args());
  }
}