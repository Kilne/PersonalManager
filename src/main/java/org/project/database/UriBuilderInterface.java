package org.project.database;

/**
 * Uri Builder abstraction.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public interface UriBuilderInterface {
    void reset();

    void withHost(String host);

    void withPort(int port);

    void withDatabase(String database);

    void withUser(String user);

    void withPassword(String password);

    String build();
}