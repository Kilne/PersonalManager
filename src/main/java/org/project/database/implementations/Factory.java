package org.project.database.implementations;

import org.project.database.abstraction.ClientInterface;

public class Factory {

    public ClientInterface<?> getClient(String clientName) {
        if (clientName == null) {
            return null;
        }
        if (clientName.equalsIgnoreCase("REDIS")) {
            return new RedisClientWrapper();
        }
        return null;
    }
}