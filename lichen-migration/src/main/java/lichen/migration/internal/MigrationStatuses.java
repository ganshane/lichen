package lichen.migration.internal;


import java.util.SortedMap;
import java.util.SortedSet;

class MigrationStatuses{
    final SortedMap<Long, Class<? extends Migration>> notInstalled;
    final SortedMap<Long, Class<? extends Migration>> installedWithAvailableImplementation;
    final SortedSet<Long> installedWithoutAvailableImplementation;

    /**
     * Container for the state of all the available and installed
     * migrations.
     *
     * @param notInstalled a sorted map of migration version numbers to
     *        Migration subclasses that are not installed in the database
     * @param installedWithAvailableImplementation a sorted map of
     *        migration version numbers to Migration subclasses that are
     *        currently installed in the database that have a matching a
     *        Migration subclass
     * @param installedWithoutAvailableImplementation a sorted set of
     *        migration version numbers that are currently installed in
     *        the database but do not have a matching a Migration subclass
     */
    MigrationStatuses(SortedMap<Long, Class<? extends Migration>> notInstalled,
                      SortedMap<Long, Class<? extends Migration>> installedWithAvailableImplementation,
                      SortedSet<Long> installedWithoutAvailableImplementation){
        this.notInstalled = notInstalled;
        this.installedWithAvailableImplementation = installedWithAvailableImplementation;
        this.installedWithoutAvailableImplementation = installedWithoutAvailableImplementation;

    }
}
