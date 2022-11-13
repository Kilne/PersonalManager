package org.project;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.project.database.implementations.Factory;
import org.project.database.implementations.RedisClientWrapper;


public class Main {
    public static void main(String[] args) {

        Argon2 argon2 = Argon2Factory.create();

        String p = "password";
        char[] password = p.toCharArray();
        System.out.println(password);

        try {
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);
            System.out.println(hash);
            // Verify password
            argon2.verify(hash, password);// Hash matches password
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

        Factory factory = new Factory();
        RedisClientWrapper client = (RedisClientWrapper) factory.getClient("REDIS");
        client.connect();
        System.out.println(client.set("key", "value"));
        System.out.println(client.get("key"));
        System.out.println(client.delete("key"));
        System.out.println(client.setClient("redis://localhost:6379"));
        System.out.println(client.set("!!!!!!", "value"));
        System.out.println(client.get("key"));

    }
}