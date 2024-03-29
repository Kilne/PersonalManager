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

    /**
     * Sets the facade to a default database.
     */
    public DatabaseFacade() {
        this.database = postgreBuilder.build();
        this.postgreBuilder.reset();
    }

    /**
     * Sets the database wrapper.
     * @param host The host.
     * @param port The port.
     * @param database The database.
     * @param user The user.
     * @param password The password.
     */
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
    public boolean connect() {

        this.database.connect();
        return this.database.isConnected();
    }

    /**
     * Disconnects from the database.
     */
    public boolean disconnect() {

        this.database.disconnect();
        return !this.database.isConnected();
    }

    /**
     * Check if the database is connected.
     *
     * @return True if the database is connected, false otherwise.
     */
    private boolean isConnected() {
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
        if ((!syncRes.isBlank() && !syncRes.isEmpty()) && !syncRes.equals("null")) {
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

    /**
     * Executes a query on the database for a specific user creating a new table if it doesn't exist.
     * @param username Username table to execute the query.
     * @param password Password table to execute the query.
     * @return True if the table was created, false otherwise.
     */
    public boolean createUserInDatabase(String username, String password) {
        String userCreation = "CREATE USER " + username + " WITH CREATEROLE PASSWORD '" + password + "';";
        String userPrivileges =
                "GRANT CONNECT ON DATABASE " + this.database.getDatabaseName() + " TO " + username + ";" +
                        "GRANT USAGE ON SCHEMA " + this.database.getDatabaseSchema() + " TO " + username + ";";
        String createUserTable = "CREATE TABLE " +
                this.database.getDatabaseSchema()+"."+username +
                "(ID SERIAL PRIMARY KEY, " +
                "NAME CHARACTER VARYING, " +
                "DUEDATE DATE, " +
                "TARGET DOUBLE PRECISION, " +
                "DESCRIPTION CHARACTER VARYING, " +
                "PROGRESS DOUBLE PRECISION, " +
                "STEPSNUMBER BIGINT, " +
                "STEPSCOMPLETED BIGINT, " +
                "STEPSVALUE DOUBLE PRECISION);"+
                "ALTER TABLE IF EXISTS "+this.database.getDatabaseSchema()+"."+username+" OWNER TO "+username+ ";"+
                "GRANT ALL ON TABLE "+this.database.getDatabaseSchema()+"."+username+" TO "+username+";";

        try{
            if(this.database.executeDatabaseAction(userCreation)
             && this.database.executeDatabaseAction(userPrivileges)
             && this.database.executeDatabaseAction(createUserTable)){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Deletes a user from the database with all his elements.
     * @param username Username table to execute the query.
     * @return True if the user was deleted, false otherwise.
     */
    public boolean removeUserFromDatabase(String username) {

        String removeUserPrivileges =
                "REVOKE CONNECT ON DATABASE " + this.database.getDatabaseName() + " FROM " + username + ";" +
                "REVOKE USAGE ON SCHEMA "+ this.database.getDatabaseSchema() +" FROM " + username + ";"+
                "REASSIGN OWNED BY "+username+" TO "+
                        this.database.select(
                                "select usename from pg_catalog.pg_user where usesuper = true")
                                .split(":")[1] +";";
        String userTableRemoval = "DROP TABLE " + this.database.getDatabaseSchema()+"."+username + ";";
        String userRemoval = "DROP USER " + username + ";";

        try{
            if( this.database.executeDatabaseAction(removeUserPrivileges)
                && this.database.executeDatabaseAction(userTableRemoval)
                && this.database.executeDatabaseAction(userRemoval)){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Manipulate the user details in the database.
     * <br>
     * @param query the query to execute.
     * @return True if the query is successful.
     */
    public boolean userDetailsManipulation(String query){

        try{

            if(this.database.executeDatabaseAction(query)){
                return true;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Returns the database host and port.
     *
     * @return The database host and port in array format.
     */
    public String[] getClientInfo() {
        return this.database.getDatabaseHostAndPort();
    }

    /**
     * Returns the database name.
     *
     * @return The database name.
     */
    public String getDatabaseName() {
        return this.database.getDatabaseName();
    }

    /**
     * Get the current user.
     *
     * @return The current user. Or an empty string if no connection is established.
     */
    public String getCurrentUser() {
        return this.database.getCurrentUser();
    }
}