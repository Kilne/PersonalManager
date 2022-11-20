package org.project.database;

/**
 * Uri Builder for PostgreSQL database.
 *
 * @param <T> Type of the object returned by the build method.
 */
@SuppressWarnings("unused")
public interface UriBuilderInterface<T> {
    void reset();

    void withHost(String host);

    void withPort(int port);

    void withDatabase(String database);

    void withUser(String user);

    void withPassword(String password);

    T build();
}