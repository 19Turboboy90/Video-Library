package ru.zharinov.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    List<T> findAll();

    Optional<T> findById(K id);

    T save(T entity) throws SQLException;

    void update(T entity);

    boolean delete(K id);
}
