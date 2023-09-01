package main.java.ru.video.library.dao;

import main.java.ru.video.library.entity.Movie;

import java.util.List;
import java.util.Optional;

public class MovieDao implements StorageDao<Integer, Movie> {
    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Movie save(Movie object) {
        return null;
    }

    @Override
    public void update(Movie object) {

    }

    @Override
    public Optional<Movie> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }
}
