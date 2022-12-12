package org.project.core;

import org.project.ORM.PersonalManagerORM;
import org.project.core.adapters.MyPostgresDataProcess;
import org.project.core.adapters.PostgreQueryBuilder;
import org.project.core.adapters.QueryType;
import org.project.database.MyPostgreWrapper;
import org.project.database.PostgreBuilder;

import java.util.ArrayList;

/**
 * Database Facade.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class DatabaseFacade {
    private final MyPostgresDataProcess dataProcess = new MyPostgresDataProcess();
    private final PostgreQueryBuilder queryBuilder = new PostgreQueryBuilder();
    private final PostgreBuilder postgreBuilder = new PostgreBuilder();
    private MyPostgreWrapper database;

    public DatabaseFacade() {
        this.database = postgreBuilder.build();
        this.postgreBuilder.reset();
    }

    public DatabaseFacade(String host, int port, String database, String user, String password) {
        this.postgreBuilder.withHost(host);
        this.postgreBuilder.withPort(port);
        this.postgreBuilder.withDatabase(database);
        this.postgreBuilder.withUser(user);
        this.postgreBuilder.withPassword(password);
        this.database = postgreBuilder.build();
        this.postgreBuilder.reset();
    }

    /**
     * Set a new database connection.
     *
     * @param host     Host of the database.
     * @param port     Port of the database.
     * @param database Database name.
     * @param user     Username.
     * @param password Password.
     */
    public void setNewConnection(String host, int port, String database, String user, String password) {
        if (this.database != null && this.database.isConnected()) {
            this.database.disconnect();
        } else {
            this.postgreBuilder.withHost(host);
            this.postgreBuilder.withPort(port);
            this.postgreBuilder.withDatabase(database);
            this.postgreBuilder.withUser(user);
            this.postgreBuilder.withPassword(password);
            this.database = this.postgreBuilder.build();
            this.postgreBuilder.reset();
        }
    }

    /**
     * Set a new database connection.
     *
     * @param host     Host of the database.
     * @param port     Port of the database.
     * @param database Database name.
     */
    public void setNewConnection(String host, int port, String database) {
        if (this.database != null && this.database.isConnected()) {
            this.database.disconnect();
        } else {
            this.postgreBuilder.withHost(host);
            this.postgreBuilder.withPort(port);
            this.postgreBuilder.withDatabase(database);
            this.database = postgreBuilder.build();
            this.postgreBuilder.reset();
        }
    }

    /**
     * Set a new database default connection.
     */
    public void setNewConnection() {
        if (this.database != null && this.database.isConnected()) {
            this.database.disconnect();
        } else {
            this.database = postgreBuilder.build();
            this.postgreBuilder.reset();
        }
    }

    /**
     * Connects to the database.
     */
    public void connect() {
        this.database.connect();
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect() {
        this.database.disconnect();
    }

    /**
     * Check if the database is connected.
     *
     * @return True if the database is connected, false otherwise.
     */
    public boolean isConnected() {
        return this.database.isConnected();
    }

    /**
     * Syncs the user data from the database.
     *
     * @param table Table name.
     * @return The user data.
     */
    private ArrayList<PersonalManagerORM> sync(String table) {
        ArrayList<PersonalManagerORM> res = new ArrayList<>();
        String syncRes = this.database.select(
                this.queryBuilder.setQueryType(QueryType.SELECT).setTable(table).buildQuery().getQuery()
        );

        if (syncRes.contains(",")) {
            String[] rows = syncRes.split(",");
            for (String row : rows) {
                this.dataProcess.setData(row.trim());
                res.add(this.dataProcess.packData());
            }
        } else {
            this.dataProcess.setData(syncRes);
            res.add(this.dataProcess.packData());
        }
        return res;
    }

    /**
     * Executes a query on the database.
     *
     * @param queryType   Query type.
     * @param user        Username table to execute the query.
     * @param ormsObjects ORM objects to execute the query.
     * @return A collection of synced data resulting from the query.
     * @see QueryType
     * @see PersonalManagerORM
     * @see org.project.ORM.ORMtypes
     */
    public ArrayList<PersonalManagerORM> queryTheDatabase(QueryType queryType, String user,
                                                          ArrayList<PersonalManagerORM> ormsObjects) {
        String query;
        switch (queryType) {
            case INSERT -> {
                if (ormsObjects != null && !ormsObjects.isEmpty()) {
                    for (PersonalManagerORM ormObject : ormsObjects) {
                        query = this.queryBuilder.setQueryType(QueryType.INSERT).setTable(user)
                                .parseArgs(this.dataProcess.unpackData(ormObject)).buildQuery().getQuery();
                        this.database.insert(query);
                        this.queryBuilder.resetAll();
                    }
                }
                return this.sync(user);
            }
            case UPDATE -> {
                if (ormsObjects != null && !ormsObjects.isEmpty()) {
                    for (PersonalManagerORM ormObject : ormsObjects) {
                        query = this.queryBuilder.setQueryType(QueryType.UPDATE).setTable(user)
                                .parseArgs(this.dataProcess.unpackData(ormObject)).buildQuery().getQuery();
                        this.database.update(query);
                        this.queryBuilder.resetAll();
                    }
                }
                return this.sync(user);
            }
            case DELETE -> {
                if (ormsObjects != null && !ormsObjects.isEmpty()) {
                    for (PersonalManagerORM ormObject : ormsObjects) {
                        query = this.queryBuilder.setQueryType(QueryType.DELETE).setTable(user)
                                .parseArgs(this.dataProcess.unpackData(ormObject)).buildQuery().getQuery();
                        this.database.delete(query);
                        this.queryBuilder.resetAll();
                    }
                }
                return this.sync(user);
            }
            case SELECT -> {
                return this.sync(user);
            }
        }
        return this.sync(user);
    }
}