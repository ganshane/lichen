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

import java.io.File;
import java.io.PrintStream;
import java.util.Date;


public class CommandLine {
    private final PrintStream _console = System.out;
    private final String[] _args;

    public CommandLine(String[] args) {
        this._args = args;
    }

    public void execute() {
        final SelectedOptions selectedOptions = CommandOptionParser.parse(_args);
        try {
            if (!validOptions(selectedOptions) || selectedOptions.needsHelp()) {
                printUsage();
            } else {
                runCommand(selectedOptions);
            }
        } catch (Exception e) {
            _console.printf("\nERROR: %s", e.getMessage());
            if (selectedOptions.isTrace()) {
                e.printStackTrace();
            }
            System.exit(1); // Issue 730
        }
    }

    private void runCommand(SelectedOptions selectedOptions) {
        final String commandString = selectedOptions.getCommand();

        _console.printf("------------------------------------------------------------------------%n");
        _console.printf("-- Lichen Migrations - %s%n", commandString);
        _console.printf("------------------------------------------------------------------------%n");

        long start = System.currentTimeMillis();
        boolean exceptionCaught = false;

        try {
            final Command command = Commands.resolveCommand(commandString.toUpperCase(), selectedOptions);
            command.execute(selectedOptions.getParams());
        } catch (Throwable t) {
            exceptionCaught = true;
            if (t instanceof MigrationException) {
                throw (MigrationException) t;
            } else {
                throw new MigrationException(t);
            }
        } finally {
            _console.printf("------------------------------------------------------------------------%n");
            String result = "SUCCESS";
            if (exceptionCaught) {
                result = "FAILURE";
            }
            _console.printf("-- Lichen Migrations %s%n", result);
            final int size = 1000;
            _console.printf("-- Total time: %ss%n", ((System.currentTimeMillis() - start) / size));
            _console.printf("-- Finished at: %s%n", new Date());
            printMemoryUsage();
            _console.printf("------------------------------------------------------------------------%n");
        }
    }

    private void printMemoryUsage() {
        final Runtime runtime = Runtime.getRuntime();
        final int megaUnit = 1024 * 1024;
        final long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / megaUnit;
        final long totalMemory = runtime.totalMemory() / megaUnit;

        _console.printf("-- Final Memory: %sM/%sM%n", usedMemory, totalMemory);
    }

    private boolean validOptions(SelectedOptions selectedOptions) {
        if (!selectedOptions.needsHelp() && selectedOptions.getCommand() == null) {
            _console.printf("No command specified.%n");
            return false;
        }

        return validBasePath(selectedOptions.getPaths().getBasePath());
    }

    private boolean validBasePath(File basePath) {
        final boolean validDirectory = basePath.exists() && basePath.isDirectory();

        if (!validDirectory) {
            _console.printf("Migrations path must be a directory: %s%n", basePath.getAbsolutePath());
        }

        return validDirectory;
    }

    private void printUsage() {
        _console.printf(
                "%nUsage: migrate command [parameter] [--path=<file>] %n%n");
        _console.printf("--path=<file>        Path to repository.  Default current working directory.%n");
        _console.printf("--config=<file>      Path to config file. Default basedir/config/migrator.xml.%n");
        _console.printf("--help               Displays this usage message.%n");
        _console.printf("--trace              Shows additional error details (if any).%n");
        _console.printf("%n");
        _console.printf("Commands:%n");
        _console.printf("  info               Display build version informations.%n");
        _console.printf("  new <description>  Creates a new migration with the provided description.%n");
        _console.printf("  up [n]             Run unapplied migrations, ALL by default, or 'n' specified.%n");
        _console.printf(
                "  down [n]           Undoes migrations applied to the database. ONE by default or 'n' specified.%n");
        _console.printf("  version <version>  Migrates the database up or down to the specified version.%n");
        _console.printf("  pending            Force executes pending migrations out of order (not recommended).%n");
        _console.printf("  status             Prints the changelog from the database if the changelog table exists.%n");
        _console.printf("%n");
        _console.printf("  * Shortcuts are accepted by using the first few (unambiguous) letters of each command..%n");
        _console.printf("%n");
    }
}
