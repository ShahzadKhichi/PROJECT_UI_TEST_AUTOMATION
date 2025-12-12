package com.sqe.framework.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class RedisManager {

    private static JedisPool jedisPool;
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(60));
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);

        jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
    }

    public static Jedis getConnection() {
        return jedisPool.getResource();
    }

    public static void set(String key, String value) {
        try (Jedis jedis = getConnection()) {
            jedis.set(key, value);
        }
    }

    public static void set(String key, String value, int expirySeconds) {
        try (Jedis jedis = getConnection()) {
            jedis.setex(key, expirySeconds, value);
        }
    }

    public static String get(String key) {
        try (Jedis jedis = getConnection()) {
            return jedis.get(key);
        }
    }

    public static void delete(String key) {
        try (Jedis jedis = getConnection()) {
            jedis.del(key);
        }
    }

    public static boolean exists(String key) {
        try (Jedis jedis = getConnection()) {
            return jedis.exists(key);
        }
    }

    // Test specific methods
    public static void storeSessionToken(String username, String token) {
        String key = "session:" + username;
        set(key, token, 3600); // 1 hour expiry
    }

    public static String getSessionToken(String username) {
        String key = "session:" + username;
        return get(key);
    }

    public static void clearSessionToken(String username) {
        String key = "session:" + username;
        delete(key);
    }

    public static void storeCartData(String userId, String cartData) {
        String key = "cart:" + userId;
        set(key, cartData, 7200); // 2 hours expiry
    }

    public static String getCartData(String userId) {
        String key = "cart:" + userId;
        return get(key);
    }

    public static void close() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
        }
    }
}