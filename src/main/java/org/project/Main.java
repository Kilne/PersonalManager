package org.project;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
    public static void main(String[] args) {

        Argon2 argon2 = Argon2Factory.create();

        String p = "password";
        char[] password = p.toCharArray();
        System.out.println(password);

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);

            // Verify password
            argon2.verify(hash, password);// Hash matches password
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        System.out.println("Hello world!");

        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = pool.getResource()) {

                System.out.println(jedis.set("foo", "bar"));

            }

        }

    }
}