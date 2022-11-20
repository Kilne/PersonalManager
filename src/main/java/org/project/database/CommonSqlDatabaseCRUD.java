package org.project.database;

/**
 * Abstraction of common family of database actions.
 *
 * @param <S> Type of the object returned by the select method.
 * @param <I> Type of the object used as input for the insert method.
 * @param <U> Type of the object used as input for the update method.
 * @param <D> Type of the object used as input for the delete method.
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public interface CommonSqlDatabaseCRUD<S, I, U, D> {

    S select(String query);

    I insert(String query);

    U update(String query);

    D delete(String query);

}