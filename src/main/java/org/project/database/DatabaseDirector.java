package org.project.database;

/**
 * Database Director
 *
 * @author Luca Maiuri
 */
public class DatabaseDirector {

    private ConnectionBuilder connectionBuilder;

    public void changeBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public void buildDatabaseManager(DatabaseTypes databaseTypes, String... databaseParameters) {
        switch (databaseTypes){
            case REDIS -> {
                if (connectionBuilder != null) {
                    connectionBuilder.createConnectionObject(databaseParameters);
                }
            }
            default -> {
                if (connectionBuilder != null) {
                    connectionBuilder.cancel();
                }
            }
        }
    }
}
