package org.project.database.redis;
import redis.clients.jedis.JedisPooled;

import java.util.Objects;

/**
 * Redis Connection Manager
 *
 * @author Luca Maiuri
 *
 */
public class RedisConnectorManager {

    private static JedisPooled jedisPool= null;
    private static final RedisConnectorManager redisConnectorManager = null;

    /**
     *  Redis Connection Manager instance
     */
    private RedisConnectorManager(JedisPooled jedisPool) {
        RedisConnectorManager.jedisPool = jedisPool;
    }

    /**
     * Get Redis Connection
     * @return JedisPool
     * @throws IllegalStateException if Redis connection is not initialized
     */
    public JedisPooled getJedisPool() {
        if (Objects.isNull(jedisPool)) {
            throw new IllegalStateException("Redis connection not initialized");
        }else {
            return jedisPool;
        }
    }

    /**
     * Set Redis Connection with a connection object
     * @return RedisConnectorManager
     */
    public static RedisConnectorManager getInstance(JedisPooled jedisPool) {
        return Objects.requireNonNullElseGet(redisConnectorManager, () -> new RedisConnectorManager(jedisPool));
    }

    /**
     * Close Redis Connection and set it to null
     */
    public static void closeConnection() {
        if (Objects.nonNull(jedisPool)) {
            jedisPool.close();
            jedisPool = null;
        }
    }
}
