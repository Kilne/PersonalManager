package org.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Wrapper for PostgreSQL database. Based on JDBC.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class MyPostgreWrapper implements CommonDatabaseActions, CommonSqlDatabaseCRUD<String, Integer, Integer, Integer> {

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

    private boolean isConnected() {
        return this.connection != null;
    }


    /**
     * Selects all the rows of a table.
     *
     * @param query The query to execute in a string format.
     * @return A string containing all the rows of the table with the format: "row1;row2;row3;...;rowN".
     */
    @Override
    public String select(String query) {
        if (this.isConnected() && query.toLowerCase().contains("select")) {
            try {
                ResultSet resultSet = this.connection.createStatement().executeQuery(query);
                ArrayList<String> result = new ArrayList<>();
                while (resultSet.next()) {
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        if (i == resultSet.getMetaData().getColumnCount() - 1) {
                            result.add(
                                    resultSet.getMetaData().getColumnName(i + 1) + ": " +
                                            resultSet.getString(i + 1) + ";"
                            );
                        } else {
                            result.add(
                                    resultSet.getMetaData().getColumnName(i + 1) + ": " +
                                            resultSet.getString(i + 1)
                            );
                        }
                    }
                }
                resultSet.close();
                return result.toString()
                        .replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .replaceAll(",", "");
            } catch (SQLException e) {
                System.err.println("Select failed.");
            }
        }
        return null;
    }

    /**
     * Inserts a row in a table.
     *
     * @param query The query to execute in a string format.
     * @return The number of rows affected by the query.
     */
    @Override
    public Integer insert(String query) {
        if (this.isConnected() && query.toLowerCase().contains("insert")) {
            try {
                return this.connection.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                System.err.println("Insert failed.");
            }
        }
        return 0;
    }

    /**
     * Updates a row in a table.
     *
     * @param query The query to execute in a string format.
     * @return The number of rows affected by the query.
     */
    @Override
    public Integer update(String query) {
        if (this.isConnected() && query.toLowerCase().contains("update")) {
            try {
                return this.connection.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                System.err.println("Update failed.");
            }
        }
        return 0;
    }

    /**
     * Deletes a row in a table.
     *
     * @param query The query to execute in a string format.
     * @return The number of rows affected by the query.
     */
    @Override
    public Integer delete(String query) {
        if (this.isConnected() && query.toLowerCase().contains("delete")) {
            try {
                return this.connection.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                System.err.println("Delete failed.");
            }
        }
        return 0;
    }
}