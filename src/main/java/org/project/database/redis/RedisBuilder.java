package org.project.database.redis;

import org.project.database.ConnectionBuilder;
import redis.clients.jedis.JedisPooled;


/**
 * Redis Connection Builder
 *
 * @author Luca Maiuri
 */
public class RedisBuilder implements ConnectionBuilder {

    private RedisConnectorManager redisConnectorManager;

    @Override
    public void cancel() {
        this.redisConnectorManager = null;
    }

    @Override
    public void createConnectionObject(String... databaseParameters) {
        this.redisConnectorManager = RedisConnectorManager.getInstance(
                new JedisPooled(databaseParameters[0],
                        Integer.parseInt(databaseParameters[1]
                        )
                )
        );
    }

    public RedisConnectorManager getRedisConnectorManager() {
        return redisConnectorManager;
    }
}
