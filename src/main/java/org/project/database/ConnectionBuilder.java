package org.project.database;

/**
 * Connection Builder interface
 *
 * @author Luca Maiuri
 */
public interface ConnectionBuilder {
    /**
     * Erase connection object
     */
    void cancel();
    /**
     * Create connection object
     * @param databaseParameters database parameters
     */
    void createConnectionObject(String... databaseParameters);
}
