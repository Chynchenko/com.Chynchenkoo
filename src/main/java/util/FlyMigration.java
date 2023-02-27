package util;

import org.flywaydb.core.Flyway;

public class FlyMigration {


    public static void migrateDB() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "3757")
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }
}