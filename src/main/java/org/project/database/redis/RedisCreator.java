package org.project.database.redis;

import org.project.database.factory.ClientFactory;
import redis.clients.jedis.JedisPooled;


/**
 * RedisCreator
 *
 * @author Luca Maiuri
 */
public class RedisCreator extends ClientFactory {


    /**
     * Create a Redis client
     * @param host host
     * @param port port
     * @return RedisBasicClient
     */
    @Override
    public RedisBasicClient createRedisClient(String host, int port) {
        return new RedisClient(new JedisPooled(host, port));
    }
}

