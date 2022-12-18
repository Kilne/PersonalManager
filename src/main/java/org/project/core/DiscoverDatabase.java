package org.project.core;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.DatagramSocket;
import java.net.ServerSocket;

public class DiscoverDatabase {

    Dotenv dotenv;

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
    }

    private boolean loadDotenv() {
        try {
            this.dotenv = Dotenv.configure().ignoreIfMalformed().load();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DatabaseFacade connectToLocalDatabase() {
        if (this.loadDotenv()) {
            if (!this.discoverLocalService(Integer.parseInt(this.dotenv.get("DB_PORT")))) {
                return new DatabaseFacade(
                        this.dotenv.get("DB_HOST"),
                        Integer.parseInt(this.dotenv.get("DB_PORT")),
                        this.dotenv.get("DB_NAME"),
                        this.dotenv.get("DB_USER"),
                        this.dotenv.get("DB_PASSWORD")
                );
            }
        }
        return null;
    }

}