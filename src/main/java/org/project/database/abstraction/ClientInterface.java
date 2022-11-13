package org.project.database.abstraction;

/**
 * Client abstraction
 *
 * @param <T> Client type
 * @author Luca Mairui
 */
public abstract class ClientInterface<T> implements CommonActions {
    protected T client;

    public abstract boolean setClient(Object... args);

}