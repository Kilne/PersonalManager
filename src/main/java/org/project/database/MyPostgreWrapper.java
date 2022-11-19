package org.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Wrapper for PostgreSQL database. Based on JDBC.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class MyPostgreWrapper implements CommonDatabaseActions {

    private Connection connection;
    private String url;

    public MyPostgreWrapper(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Connection failed.");
        }

    }

    @Override
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                System.err.println("Disconnection failed.");
            }
        }
    }

}