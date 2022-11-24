package org.project.database;

/**
 * Uri Builder for PostgreSQL database.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class PostgreBuilder implements UriBuilderInterface<MyPostgreWrapper> {

    private String buildedUrl = "jdbc:postgresql://localhost:5432/postgres";
    private MyPostgreWrapper postgreWrapper;

    @Override
    public void reset() {
        this.postgreWrapper = null;
        this.buildedUrl = "jdbc:postgresql://localhost:5432/postgres";
    }

    @Override
    public void withHost(String host) {
        this.buildedUrl = this.buildedUrl.replace("localhost", host);
    }

    @Override
    public void withPort(int port) {
        this.buildedUrl = this.buildedUrl.replace("5432", String.valueOf(port));
    }

    @Override
    public void withDatabase(String database) {
        this.buildedUrl = this.buildedUrl.replace("postgres", database);
    }

    @Override
    public void withUser(String user) {
        if (this.buildedUrl.contains("?")) {
            this.buildedUrl = this.buildedUrl + "&user=" + user;
        } else {
            this.buildedUrl = this.buildedUrl + "?user=" + user;
        }
    }

    @Override
    public void withPassword(String password) {
        if (this.buildedUrl.contains("?")) {
            this.buildedUrl = this.buildedUrl + "&password=" + password;
        } else {
            this.buildedUrl = this.buildedUrl + "?password=" + password;
        }
    }

    /**
     * Builds the database wrapper.
     *
     * @return The database wrapper.
     */
    @Override
    public MyPostgreWrapper build() {
        if (this.postgreWrapper == null) {
            this.postgreWrapper = new MyPostgreWrapper(this.buildedUrl);
        }
        return this.postgreWrapper;
    }


}