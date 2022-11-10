package org.project.database.redis;

import org.project.database.operations.CrudOperations;

public class RedisClient implements CrudOperations {

    private final RedisConnectorManager redisConnectorManager;

    public RedisClient(RedisConnectorManager redisConnectorManager) {
        this.redisConnectorManager = redisConnectorManager;
    }


    /**
     * Create a new record
     *
     * @param key   key of the record
     * @param value value to be stored
     * @param <T>   type of key and value
     * @return true if operation is successful
     * @throws IllegalArgumentException if key is not a string
     */
    @Override
    public <T> boolean create(T key, T value) {
        if(key instanceof String) {
            return redisConnectorManager.getJedisPool().set((String) key, (String) value).equals("OK");
        }else {
            throw new IllegalArgumentException("Key must be a String");
        }

    }

    @Override
    public <T> T read(T key) {
        return null;
    }

    @Override
    public <T> boolean update(T key, T value) {
        return false;
    }

    @Override
    public <T> boolean delete(T key) {
        return false;
    }
}

