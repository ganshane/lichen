package lichen.migration.internal.options;

import java.io.File;

public class SelectedPaths {
  private File basePath = new File("./");
  private File envPath;
  private File scriptPath;
  private File driverPath;

  public File getBasePath() {
    return basePath;
  }

  public File getEnvPath() {
    return envPath == null ? CommandOptionParser.file(basePath, "./environments") : envPath;
  }

  public File getScriptPath() {
    return scriptPath == null ? CommandOptionParser.file(basePath, "./scripts") : scriptPath;
  }

  public File getDriverPath() {
    return driverPath == null ? CommandOptionParser.file(basePath, "./drivers") : driverPath;
  }

  public void setBasePath(File aBasePath) {
    basePath = aBasePath;
  }

  public void setEnvPath(File aEnvPath) {
    envPath = aEnvPath;
  }

  public void setScriptPath(File aScriptPath) {
    scriptPath = aScriptPath;
  }

  public void setDriverPath(File aDriverPath) {
    driverPath = aDriverPath;
  }
}
