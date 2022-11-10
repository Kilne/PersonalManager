package org.project.database.redis;

import redis.clients.jedis.JedisPooled;

/**
 * RedisClient
 *
 * @author Luca Maiuri
 */
public class RedisClient implements RedisBasicClient {

    private JedisPooled jedisPooled;
    
    public RedisClient(JedisPooled jedisPooled) {
        this.jedisPooled = jedisPooled;
    }

    /**
     * @param key 
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
        return false;
    }

    /**
     * @param key 
     * @return
     */
    @Override
    public String get(String key) {
        return null;
    }

    /**
     * @param key 
     * @return
     */
    @Override
    public boolean delete(String key) {
        return false;
    }

    /**
     * @param key 
     * @param value
     * @return
     */
    @Override
    public boolean update(String key, String value) {
        return false;
    }

    /**
     * @return 
     */
    @Override
    public String ping() {
        return null;
    }
}
