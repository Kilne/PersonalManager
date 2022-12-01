package org.project.core.adapters;

/**
 * DB data adapter.
 *
 * @param <T> The type of the data to adapt.
 */
public interface AdapterDataDB<T> {
    T packData();

    String unpackData(T data);
}