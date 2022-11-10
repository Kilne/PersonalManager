package org.project.database.redis;

public interface RedisBasicClient {
    boolean set(String key, String value);
    String get(String key);
    boolean delete(String key);
    boolean update(String key, String value);
    String ping();
}
