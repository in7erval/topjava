package ru.javawebinar.topjava.util;

import java.util.Collection;

public interface DB<T> {
    void save(T t);

    void delete(int id);

    T get(int id);

    Collection<T> getAll();
}
