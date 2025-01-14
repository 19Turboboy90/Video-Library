package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import ru.zharinov.entity.Director;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorDao implements Dao<Integer, Director> {
    private static final DirectorDao INSTANCE = new DirectorDao();

    private static final String FIND_DIRECTOR_BY_MOVIE_ID = """
            
            """;

    @Override
    public List<Director> findAll() {
        return List.of();
    }

    @Override
    public Optional<Director> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Director save(Director entity) {
        return null;
    }

    @Override
    public void update(Director entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public static DirectorDao getInstance() {
        return INSTANCE;
    }
}
