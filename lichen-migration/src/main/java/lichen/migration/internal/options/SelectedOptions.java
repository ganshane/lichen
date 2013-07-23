package lichen.migration.internal.options;

public class SelectedOptions {
  private SelectedPaths paths = new SelectedPaths();
  private String command;
  private String params;
  private boolean trace;
  private boolean help;


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
