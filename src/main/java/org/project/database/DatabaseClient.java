package org.project.database;

public interface DatabaseClient {

    boolean connect();

    void disconnect();

    boolean isConnected();

    <T> T executeQuery(Object... params);

    <T> T executeUpdate(Object... params);

    <T> T delete(Object... params);
}