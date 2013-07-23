package lichen.migration.internal.options;

import java.io.File;


public enum CommandOptionParser {
  ;

  public static SelectedOptions parse(String[] args) {
    final SelectedOptions selectedOptions = new SelectedOptions();

    for (String arg : args) {
      final boolean isOption = isOption(arg);
      if (isOption) {
        parseOptions(arg, selectedOptions);
      } else {
        setCommandOrAppendParams(arg, selectedOptions);
      }
    }

    return selectedOptions;
  }

  private static void setCommandOrAppendParams(String arg, SelectedOptions options) {
    if (options.getCommand() == null) {
      options.setCommand(arg);
    } else {
      final String myParams = options.getParams() == null ? arg : options.getParams() + " " + arg;
      options.setParams(myParams);
    }
  }

  private static boolean parseOptions(String arg, SelectedOptions options) {
    final boolean isOption = isOption(arg);

    if (isOption) {
      final String[] argParts = arg.substring(2).split("=");
      final CommandOption option = CommandOption.valueOf(argParts[0].toUpperCase());

      switch (option) {
        case PATH:
          options.getPaths().setBasePath(new File(argParts[1]));
          break;
        case HELP:
          options.setHelp(true);
          break;
      }
    }

    return isOption;
  }
    public static boolean isOption(String arg) {
        return arg.startsWith("--") && !arg.trim().endsWith("=");
    }

    public static File file(File path, String fileName) {
        return new File(path.getAbsolutePath() + File.separator + fileName);
    }
}
