package lichen.migration.internal;

import java.io.File;

enum CommandOptionParser {
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
                case CONFIG:
                    options.getPaths().setConfigPath(new File(argParts[1]));
                    break;
                case TRACE:
                    options.setTrace(true);
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
class SelectedPaths {
    private File basePath = new File("./");
    private File configPath;

    public File getBasePath() {
        return basePath;
    }
    public void setBasePath(File aBasePath) {
        basePath = aBasePath;
    }

    public File getLibPath() {
        return new File(basePath,"./lib");
    }

    public File getConfigPath() {
        if(configPath == null)
            return new File(basePath,"./config/migrator.xml");
        return configPath;
    }

    public void setConfigPath(File configPath) {
        this.configPath = configPath;
    }
}
class SelectedOptions {
    private SelectedPaths paths = new SelectedPaths();
    private String command;
    private String params;
    private boolean trace;
    private boolean help;
    private File configPath;


    public SelectedPaths getPaths() {
        return paths;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String aCommand) {
        command = aCommand;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String aParams) {
        params = aParams;
    }

    public boolean needsHelp() {
        return help;
    }

    public void setHelp(boolean aHelp) {
        help = aHelp;
    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

}
enum CommandOption {
    PATH,
    TRACE,
    HELP,
    CONFIG,
}
