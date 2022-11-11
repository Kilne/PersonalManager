package org.project.database;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;

/**
 * Redis client implementation
 *
 * @author Luca Maiuri
 */
public class DatabaseRedis implements DatabaseClient {

    private final RedisClient redisClient;

    /**
     * Constructor for local Redis server
     */
    public DatabaseRedis() {
        redisClient = RedisClient.create(RedisURI.create("redis://localhost"));
    }

    /**
     * Constructor for remote Redis server with host and port
     *
     * @param host Redis server host
     * @param port Redis server port
     */
    public DatabaseRedis(String host, int port) {
        redisClient = RedisClient.create(RedisURI.create(host, port));
    }

    /**
     * Connection to Redis server
     *
     * @return true if connection is successful
     */
    @Override
    public boolean connect() {
        try {
            redisClient.connect();
            // TODO: LOG?
            return true;
        } catch (RedisConnectionException e) {
            // TODO: LOG?
            return false;
        }
    }

    /**
     *
     */
    @Override
    public void disconnect() {

    }

    /**
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T executeQuery(Object... params) {
        return null;
    }

    /**
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T executeUpdate(Object... params) {
        return null;
    }

    /**
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T delete(Object... params) {
        return null;
    }
}