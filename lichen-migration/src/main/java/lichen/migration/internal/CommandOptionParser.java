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
            final String myParams;
            if (options.getParams() == null) {
                myParams = arg;
            } else {
                myParams = options.getParams() + " " + arg;
            }
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
                default:
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
    private File _basePath = new File("./");
    private File _configPath;

    public File getBasePath() {
        return _basePath;
    }

    public void setBasePath(File aBasePath) {
        _basePath = aBasePath;
    }

    public File getLibPath() {
        return new File(_basePath, "./lib");
    }

    public File getConfigPath() {
        if (_configPath == null) {
            return new File(_basePath, "./config/migrator.xml");
        }
        return _configPath;
    }

    public void setConfigPath(File configPath) {
        this._configPath = configPath;
    }
}

class SelectedOptions {
    private SelectedPaths _paths = new SelectedPaths();
    private String _command;
    private String _params;
    private boolean _trace;
    private boolean _help;
    private File _configPath;


    public SelectedPaths getPaths() {
        return _paths;
    }


    public String getCommand() {
        return _command;
    }

    public void setCommand(String aCommand) {
        _command = aCommand;
    }

    public String getParams() {
        return _params;
    }

    public void setParams(String aParams) {
        _params = aParams;
    }

    public boolean needsHelp() {
        return _help;
    }

    public void setHelp(boolean aHelp) {
        _help = aHelp;
    }

    public boolean isTrace() {
        return _trace;
    }

    public void setTrace(boolean trace) {
        this._trace = trace;
    }

}

enum CommandOption {
    PATH,
    TRACE,
    HELP,
    CONFIG,
}
