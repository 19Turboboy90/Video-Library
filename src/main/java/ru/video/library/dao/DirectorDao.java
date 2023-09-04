package main.java.ru.video.library.dao;

import main.java.ru.video.library.entity.Director;

import java.util.List;
import java.util.Optional;

public class DirectorDao implements StorageDao<Integer, Director> {
    private static final DirectorDao INSTANCE = new DirectorDao();

    private DirectorDao() {
    }

    public static DirectorDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Director save(Director director) {
        return null;
    }

    @Override
    public void update(Director director) {

    }

    @Override
    public Optional<Director> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Director> findAll() {
        return null;
    }
}
