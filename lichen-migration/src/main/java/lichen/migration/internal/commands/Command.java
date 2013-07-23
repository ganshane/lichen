package lichen.migration.internal.commands;

public interface Command {
  void execute(String... params);
}
