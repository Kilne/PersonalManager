package org.project.database.factory;

import org.project.database.redis.RedisBasicClient;

public abstract class ClientFactory {

    public abstract RedisBasicClient createRedisClient(String host, int port);

}
