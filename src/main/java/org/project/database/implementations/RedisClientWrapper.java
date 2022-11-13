package org.project.database.implementations;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.project.database.abstraction.ClientInterface;

/**
 * Redis client wrapper
 *
 * @author Luca Mairui
 */
public class RedisClientWrapper extends ClientInterface<RedisClient> {

    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;
    private RedisURI redisURI;

    /**
     * Connect to the redis default localhost server
     */
    public RedisClientWrapper() {
        redisURI = RedisURI.create("redis://localhost:6379");
        client = RedisClient.create(redisURI);
    }

    /**
     * Connect to the redis server
     *
     * @param host redis server host
     * @param port redis server port
     */
    public RedisClientWrapper(String host, int port) {
        redisURI = RedisURI.builder().withHost(host).withPort(port).build();
        client = RedisClient.create(redisURI);
    }

    /**
     * Connect to Redis
     *
     * @return True if connection is successful
     */
    @Override
    public boolean connect() {
        try {
            connection = client.connect();
            syncCommands = connection.sync();
            return true;
        } catch (RedisException e) {
            System.err.println("Error connecting to Redis: " + e.getMessage());
            return false;
        }
    }

    /**
     * Disconnect from Redis
     *
     * @return True if disconnection is successful
     */
    @Override
    public boolean disconnect() {
        try {
            connection.close();
            client.shutdown();
            return true;
        } catch (RedisException e) {
            System.err.println("Error disconnecting from Redis: " + e.getMessage());
            return false;
        }
    }

    /**
     * Service method to check if arguments are valid or connection is established
     *
     * @param args Arguments
     * @return True if arguments are valid and  connection is established
     */
    private boolean checks(Object[] args) {
        if (args.length != 2 || !(args[0] instanceof String)) {
            System.err.println("Wrong number or type of arguments");
            return false;
        }
        if (connection == null || syncCommands == null || !connection.isOpen()) {
            System.err.println("Connection to Redis is not established");
            return false;
        }
        return true;
    }

    /**
     * Set a new client closing the existing one
     *
     * @param args Arguments to create the new client<br>
     *             args[0] = host<br>
     *             args[1] = port
     * @return True if the new client is created
     */
    @Override
    public boolean setClient(Object... args) {
        this.connection.close();
        this.client.shutdown();
        switch (args.length) {
            case 0 -> {
                this.client = RedisClient.create(redisURI);
                connect();
                return true;
            }
            case 1 -> {
                if (args[0] instanceof String) {
                    this.redisURI = RedisURI.create((String) args[0]);
                    this.client = RedisClient.create(redisURI);
                    connect();
                } else {
                    System.err.println("Wrong type of arguments");
                }
                return true;
            }
            case 2 -> {
                if (args[0] instanceof String && args[1] instanceof Integer) {
                    this.redisURI = RedisURI.builder().withHost((String) args[0]).withPort((Integer) args[1]).build();
                    this.client = RedisClient.create(redisURI);
                    connect();
                } else {
                    System.err.println("Wrong type of arguments");
                }
                return true;
            }
            default -> {
                System.err.println("Wrong number of arguments");
                return false;
            }
        }
    }

    /**
     * Set a key-value pair
     *
     * @param key   Key
     * @param value Value
     * @return True if the key-value pair is set
     */
    public boolean set(String key, String value) {
        return syncCommands.set(key, value).equals("OK");
    }

    /**
     * Get the value of a key
     *
     * @param key Key
     * @return Value of the key
     */
    public String get(String key) {
        if (syncCommands.exists(key) == 1) {
            return syncCommands.get(key);
        } else {
            return null;
        }
    }

    /**
     * Delete a key
     *
     * @param key Key
     * @return True if the key is deleted
     */
    public boolean delete(String key) {
        return syncCommands.del(key) == 1;
    }
}