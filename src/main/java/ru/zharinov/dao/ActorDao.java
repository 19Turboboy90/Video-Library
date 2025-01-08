package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Actor;
import ru.zharinov.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorDao implements Dao<Integer, Actor> {
    private static final ActorDao INSTANCE = new ActorDao();

    public static final String GET_ALL_ACTORS_BY_MOVIE_ID = """
            SELECT a.id, a.name, a.date_of_birth
            FROM actor a
                     LEFT JOIN actor_movie am ON a.id = am.actor_id
                     LEFT JOIN movie m ON am.movie_id = m.id
            WHERE m.id = ?;
            """;

    @Override
    public List<Actor> findAll() {
        return List.of();
    }

    @Override
    public Optional<Actor> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Actor save(Actor entity) {
        return null;
    }

    @Override
    public void update(Actor entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @SneakyThrows
    public List<Actor> findAllActorByMovieId(Integer movieId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_ACTORS_BY_MOVIE_ID)) {
            preparedStatement.setObject(1, movieId);
            List<Actor> actors = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actors.add(biuldActor(resultSet));
            }
            return actors;
        }
    }

    private Actor biuldActor(ResultSet resultSet) throws SQLException {
        return Actor.builder()
                .id(resultSet.getObject("a.id", Integer.class))
                .name(resultSet.getObject("a.name", String.class))
                .dateOfBirthday(resultSet.getObject("a.date_of_birth", Date.class).toLocalDate())
                .build();
    }

    public static ActorDao getInstance() {
        return INSTANCE;
    }
}

