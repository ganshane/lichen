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
        case ENVPATH:
          options.getPaths().setEnvPath(new File(argParts[1]));
          break;
        case SCRIPTPATH:
          options.getPaths().setScriptPath(new File(argParts[1]));
          break;
        case DRIVERPATH:
          options.getPaths().setDriverPath(new File(argParts[1]));
          break;
        case ENV:
          options.setEnvironment(argParts[1]);
          break;
        case FORCE:
          options.setForce(true);
          break;
        case TRACE:
          options.setTrace(true);
          break;
        case HELP:
          options.setHelp(true);
          break;
        case TEMPLATE:
          options.setTemplate(argParts[1]);
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
