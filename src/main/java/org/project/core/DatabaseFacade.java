package org.project.core;

import org.project.ORM.CommonORM;
import org.project.ORM.FactoryORM;
import org.project.ORM.PersonalManagerORM;
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
    private final MyPostgreWrapper database;
    private final FactoryORM factoryORM = new FactoryORM();

    public DatabaseFacade() {
        this.database = new PostgreBuilder().build();

    }

    public DatabaseFacade(String host, int port, String database, String user, String password) {
        PostgreBuilder postgreBuilder = new PostgreBuilder();
        postgreBuilder.withHost(host);
        postgreBuilder.withPort(port);
        postgreBuilder.withDatabase(database);
        postgreBuilder.withUser(user);
        postgreBuilder.withPassword(password);
        this.database = postgreBuilder.build();
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
     * Selects all values of a table and packs it a specific ORM.
     *
     * @param tablename The name of the table to select.
     * @param ormType   The name of ORM to use.
     * @param type      The type of the ORM to use.
     * @param <T>       The type of the ORM to use.
     * @return An ArrayList of ORM objects.
     */
    private <T extends CommonORM> ArrayList<T> packData(String tablename, String ormType, Class<T> type) {
        ArrayList<T> data = new ArrayList<>();
        String query = "SELECT * FROM " + tablename;
        String[] rows = this.database.select(query).split(";");

        for (String row : rows) {
            factoryORM.buildORM(ormType);
            CommonORM orm = factoryORM.getORM();
            orm.parse(row);
            data.add(type.cast(orm));
        }
        return data;
    }

    public ArrayList<PersonalManagerORM> query(QueryType queryType, String... queryValues) {

        switch (queryType) {
            case SELECT:

            case INSERT:

            case UPDATE:
                return this.database.update(queryValues);
            case DELETE:
                return this.database.delete(queryValues);
            default:
                return null;
        }
    }


}