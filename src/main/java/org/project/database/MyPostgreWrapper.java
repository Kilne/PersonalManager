package org.project.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Wrapper for PostgreSQL database. Based on JDBC.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class MyPostgreWrapper implements CommonDatabaseActions,
        CommonSqlDatabaseCRUD<String, Integer, Integer, Integer> {

    private Connection connection;
    private String url;

    protected MyPostgreWrapper(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get url of the database.
     *
     * @return Url of the database.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Check if the connection is established.
     *
     * @return True if the connection is established, false otherwise.
     */
    public boolean isConnected() {
        if (this.connection == null) {
            return false;
        }
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            System.err.println(e.getErrorCode() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Connect to the database.
     */
    @Override
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Connection failed.");
        }

    }

    /**
     * Disconnect from the database.
     */
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

    /**
     * Selects all the rows of a table and packs them into a single string
     * obtained from an <code>ArrayList</code> of <code>String</code>.
     *
     * @param query The query to execute in a string format.
     * @return A string  containing all the rows of the table in a string format.
     */
    @Override
    public String select(String query) {
        if (this.isConnected() && query.toLowerCase().contains("select")) {
            try {
                ResultSet resultSet = this.connection.createStatement().executeQuery(query);
                ArrayList<String> result = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSet.next()) {

                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {

                        stringBuilder.append(resultSet.getMetaData().getColumnName(i + 1))
                                .append(":")
                                .append(resultSet.getString(i + 1))
                                .append(" ");
                    }
                    result.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
                resultSet.close();
                return result.toString().replaceAll("[\\[\\]]", "");
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

    /**
     * Executes a query which is not a select, insert, update or delete.
     *
     * @param query The query to execute in a string format.
     * @return True if the query is executed successfully, false otherwise.
     */
    public boolean executeDatabaseAction(String query) {
        if (this.isConnected()) {
            try {
                return this.connection.createStatement().execute(query);
            } catch (SQLException e) {
                System.err.println("Action failed.");
            }
        }
        return false;
    }

    /**
     * Gets the database name.
     *
     * @return The database name.Or an empty string if the connection is not established.
     */
    public String getDatabaseName() {
        if (this.isConnected()) {
            try {
                ResultSet resultSet = this.connection.getMetaData().getCatalogs();
                ArrayList<String> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
                resultSet.close();
                return result.toString().replaceAll("[\\[\\]]", "");
            } catch (SQLException e) {
                return "";
            }
        }
        return "";
    }

    /**
     * Get the schema of the database.
     * @return The schema of the database. Or empty array if the connection is not established.
     */
    public String getDatabaseSchema() {
        ArrayList<String> result = new ArrayList<>();
        if (this.isConnected()) {
            try {
                ResultSet resultSet = this.connection.getMetaData().getSchemas();
                while (resultSet.next()) {
                    if (!resultSet.getString(1).equals("information_schema")
                            && !resultSet.getString(1).equals("pg_catalog")) {
                        result.add(resultSet.getString(1));
                    }
                }
                resultSet.close();
            } catch (SQLException e) {
                return result.toString().replaceAll("[\\[\\]]", "");
            }
        }
        return result.toString().replaceAll("[\\[\\]]", "");
    }
}