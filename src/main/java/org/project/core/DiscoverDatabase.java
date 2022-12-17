package org.project.core;

import java.lang.reflect.Array;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscoverDatabase {

    /**
     * Check if a port is available.
     *
     * @param port Port to check.
     * @return True if the port is available, false otherwise.
     */
    private boolean discoverLocalService(int port) {
        try (var ignored = new ServerSocket(port); var ignored1 = new DatagramSocket(port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }// TODO questo è da rifare
    public HashMap<Integer, ArrayList<String>> discoverLocalDatabases(int port) {
        HashMap<Integer, ArrayList<String>> databases = new HashMap<>();

        if (!discoverLocalService(port)) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + port + "/");
                ResultSet resultSet = connection.getMetaData().getCatalogs();
                ArrayList<String> databaseInfo = new ArrayList<>();
                while (resultSet.next()) {
                    String databaseName = resultSet.getString(1);
                    databaseInfo.add(databaseName);
                }
                connection.close();
                databases.put(port, databaseInfo);
            } catch (SQLException e) {
                System.err.println(e.getErrorCode() + ": " + e.getMessage());
                return databases;
            }
        }
        return databases;
    }

}
