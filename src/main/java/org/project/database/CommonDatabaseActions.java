package org.project.database;

/**
 * Abstraction of common family of database actions.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public interface CommonDatabaseActions {

    void connect();

    void disconnect();

}