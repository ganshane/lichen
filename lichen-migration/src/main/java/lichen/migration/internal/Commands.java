// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.migration.internal;

import lichen.migration.config.MigratorConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            case UP:
                return new UpCommand(selectedOptions);
      /*
      case INIT:
        return new InitializeCommand(selectedOptions);
      case BOOTSTRAP:
        return new BootstrapCommand(selectedOptions);
      case NEW:
        return new NewCommand(selectedOptions);
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

final class UpCommand implements Command {
    private SelectedOptions _selectedOptions;

    UpCommand(SelectedOptions selectedOptions) {
        this._selectedOptions = selectedOptions;
    }

    @Override
    public void execute(String... params) {
        if (!_selectedOptions.getPaths().getConfigPath().exists()) {
            throw new MigrationException(String.format(
                    "config file %s doesn't exists!", _selectedOptions
                    .getPaths().getConfigPath().getAbsolutePath()));
        }
        try {
            InputStream configInputStream = new FileInputStream(_selectedOptions
                    .getPaths().getConfigPath());
            MigratorConfig config = XmlLoader.parseXML(MigratorConfig.class,
                    configInputStream, Option.Some(getClass()
                    .getResourceAsStream("/migrator-config.xsd")));

            DatabaseAdapter databaseAdapter = DatabaseAdapter.forVendor(
                    DatabaseVendor.forDriver(config._driverClassName), Option
                    .<String>None());
            Migrator migrator = new Migrator(config._url, config._username,
                    config._password, databaseAdapter);
            migrator.migrate(MigratorOperation.InstallAllMigrations,
                    config._migratePackage, false);
        } catch (FileNotFoundException e) {
            throw new MigrationException(String.format(
                    "config file %s doesn't exists!", _selectedOptions
                    .getPaths().getConfigPath().getAbsolutePath()));
        } catch (Throwable throwable) {
            throw new MigrationException(throwable);
        }
    }
}

final class InfoCommand implements Command {
    private final PrintStream _out;

    public InfoCommand(PrintStream out) {
        this._out = out;
    }

    public void execute(String... params) {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(
                "META-INF/maven/com.ganshane.lichen/lichen-migration/pom.properties");

        if (input != null) {
            try {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
                // ignore, just don't load the properties
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    // close quietly
                }
            }
        }

        _out.printf("%s %s (%s)%n",
                properties.getProperty("name"),
                properties.getProperty("version"),
                properties.getProperty("build"));
        _out.printf("Java version: %s, vendor: %s%n",
                System.getProperty("java.version"),
                System.getProperty("java.vendor"));
        _out.printf("Java home: %s%n", System.getProperty("java.home"));
        _out.printf("Default locale: %s_%s, platform encoding: %s%n",
                System.getProperty("user.language"),
                System.getProperty("user.country"),
                System.getProperty("sun.jnu.encoding"));
        _out.printf("OS name: \"%s\", version: \"%s\", arch: \"%s\", family: \"%s\"%n",
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"),
                getOsFamily());
    }

    private static String getOsFamily() {
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
