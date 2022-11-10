package org.project.database.operations;

/**
 * CRUD Operations interface
 *
 * @author Luca Maiuri
 */
public interface CrudOperations {

    /**
     * Create a new record
     *
     * @param key  key
     * @param value value
     * @param <T> type
     * @return  true if operation is successful
     */
    <T> boolean create(T key, T value);
    /**
     * Read an entry from the database
     * @param key key
     * @return The corresponding value or null if the key does not exist
     */
    <T> T read(T key);
    /**
     * Update an entry in the database
     * @param key key
     * @param value value
     * @return true if operation is successful
     */
    <T> boolean update(T key, T value);
    /**
     * Delete an entry from the database
     * @param key key
     * @return true if the operation is successful
     */
    <T> boolean delete(T key);
}
