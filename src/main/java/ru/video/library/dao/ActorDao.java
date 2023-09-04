package main.java.ru.video.library.dao;

import main.java.ru.video.library.entity.Actor;

import java.util.List;
import java.util.Optional;

public class ActorDao implements StorageDao<Integer, Actor> {
    private static final ActorDao INSTANCE = new ActorDao();

    private ActorDao() {
    }

    public static ActorDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Actor save(Actor actor) {
        return null;
    }

    @Override
    public void update(Actor actor) {

    }

    @Override
    public Optional<Actor> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Actor> findAll() {
        return null;
    }
}
