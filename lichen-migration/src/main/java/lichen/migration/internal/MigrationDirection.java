package lichen.migration.internal;

/**
 * Sealed abstract class that defines the direction to run a
 * migration.
 */
enum MigrationDirection {
    Up,Down
}
enum MigratorOperation{
    InstallAllMigrations,
    RemoveAllMigrations,
    MigrateToVersion,
    RollbackMigration;
    int version;
    int count;
}
