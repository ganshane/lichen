package lichen.migration.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public enum Commands {
  INFO,
  INIT,
  BOOTSTRAP,
  NEW,
  UP,
  DOWN,
  PENDING,
  SCRIPT,
  VERSION,
  STATUS;

  public static Command resolveCommand(String commandString, SelectedOptions selectedOptions) {
    for (Commands command : values()) {
      if (command.name().startsWith(commandString)) {
        return createCommand(command, selectedOptions);
      }
    }

    throw new MigrationException("Attempt to execute unknown command: " + commandString);
  }

  private static Command createCommand(Commands aResolvedCommand, SelectedOptions selectedOptions) {
    switch (aResolvedCommand) {
      case INFO:
        return new InfoCommand(System.out);
      /*
      case INIT:
        return new InitializeCommand(selectedOptions);
      case BOOTSTRAP:
        return new BootstrapCommand(selectedOptions);
      case NEW:
        return new NewCommand(selectedOptions);
      case UP:
        return new UpCommand(selectedOptions);
      case DOWN:
        return new DownCommand(selectedOptions);
      case PENDING:
        return new PendingCommand(selectedOptions);
      case SCRIPT:
        return new ScriptCommand(selectedOptions);
      case VERSION:
        return new VersionCommand(selectedOptions);
      case STATUS:
        return new StatusCommand(selectedOptions);
        */
      default:
        return new Command() {
          public void execute(String... params) {
            System.out.println("unknown command");
          }
        };
    }
  }
}
interface Command {
    void execute(String... params);
}

final class InfoCommand implements Command {
    private final PrintStream out;

    public InfoCommand(PrintStream out) {
        this.out = out;
    }

    public void execute(String... params) {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(
                "META-INF/maven/com.ganshane.lichen/lichen-migration/pom.properties");

        if (input != null) {
            try {
                properties.load(input);
            } catch (IOException e) {
                // ignore, just don't load the properties
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    // close quietly
                }
            }
        }

        out.printf("%s %s (%s)%n",
                properties.getProperty("name"),
                properties.getProperty("version"),
                properties.getProperty("build"));
        out.printf("Java version: %s, vendor: %s%n",
                System.getProperty("java.version"),
                System.getProperty("java.vendor"));
        out.printf("Java home: %s%n", System.getProperty("java.home"));
        out.printf("Default locale: %s_%s, platform encoding: %s%n",
                System.getProperty("user.language"),
                System.getProperty("user.country"),
                System.getProperty("sun.jnu.encoding"));
        out.printf("OS name: \"%s\", version: \"%s\", arch: \"%s\", family: \"%s\"%n",
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"),
                getOsFamily());
    }

    private static final String getOsFamily() {
        String osName = System.getProperty("os.name").toLowerCase();
        String pathSep = System.getProperty("path.separator");

        if (osName.indexOf("windows") != -1) {
            return "windows";
        } else if (osName.indexOf("os/2") != -1) {
            return "os/2";
        } else if (osName.indexOf("z/os") != -1 || osName.indexOf("os/390") != -1) {
            return "z/os";
        } else if (osName.indexOf("os/400") != -1) {
            return "os/400";
        } else if (pathSep.equals(";")) {
            return "dos";
        } else if (osName.indexOf("mac") != -1) {
            if (osName.endsWith("x")) {
                return "mac"; // MACOSX
            }
            return "unix";
        } else if (osName.indexOf("nonstop_kernel") != -1) {
            return "tandem";
        } else if (osName.indexOf("openvms") != -1) {
            return "openvms";
        } else if (pathSep.equals(":")) {
            return "unix";
        }

        return "undefined";
    }

}
