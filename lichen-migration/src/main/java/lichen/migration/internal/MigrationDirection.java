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

import lichen.migration.services.Migration;

import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Sealed abstract class that defines the direction to run a
 * migration.
 */
enum MigrationDirection {
    Up, Down
}

enum MigratorOperation {
    InstallAllMigrations,
    RemoveAllMigrations,
    MigrateToVersion,
    RollbackMigration;
    private int _version;
    private int _count;

    protected int getVersion() {
        return _version;
    }
    protected void setVersion(int version) {
        this._version = version;
    }
    protected int getCount() {
        return _count;
    }
    protected void setCount(int count) {
        this._count = count;
    }

}

class MigrationStatuses {
    private final SortedMap<Long, Class<? extends Migration>> _notInstalled;
    private final SortedMap<Long, Class<? extends Migration>> _installedWithAvailableImplementation;
    private final SortedSet<Long> _installedWithoutAvailableImplementation;

    protected SortedMap<Long, Class<? extends Migration>> getNotInstalled() {
        return _notInstalled;
    }
    protected SortedMap<Long, Class<? extends Migration>> getInstalledWithAvailableImplementation() {
        return _installedWithAvailableImplementation;
    }
    protected SortedSet<Long> getInstalledWithoutAvailableImplementation() {
        return _installedWithoutAvailableImplementation;
    }

    /**
     * Container for the state of all the available and installed
     * migrations.
     *
     * @param newNotInstalled a sorted map of migration version numbers to
     *                        MigrationHelper subclasses that are not installed in the database
     * @param newInstalledWithAvailableImplementation
     *                        a sorted map of
     *                        migration version numbers to MigrationHelper subclasses that are
     *                        currently installed in the database that have a matching a
     *                        MigrationHelper subclass
     * @param newInstalledWithoutAvailableImplementation
     *                        a sorted set of
     *                        migration version numbers that are currently installed in
     *                        the database but do not have a matching a MigrationHelper subclass
     */
    MigrationStatuses(SortedMap<Long, Class<? extends Migration>> newNotInstalled,
                      SortedMap<Long, Class<? extends Migration>> newInstalledWithAvailableImplementation,
                      SortedSet<Long> newInstalledWithoutAvailableImplementation) {
        this._notInstalled = newNotInstalled;
        this._installedWithAvailableImplementation = newInstalledWithAvailableImplementation;
        this._installedWithoutAvailableImplementation = newInstalledWithoutAvailableImplementation;
    }
}
