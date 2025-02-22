package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Actor;
import ru.zharinov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorDao implements Dao<Integer, Actor> {
    private static final ActorDao INSTANCE = new ActorDao();

    private static final String FIND_ALL_ACTORS = """
            SELECT a.id AS actor_id,
                   a.name AS actor_name,
                   a.date_of_birth AS actor_date_of_birth
            FROM actor a
            """;

    private static final String FIND_ACTORS_BY_PREFIX = FIND_ALL_ACTORS + """
            WHERE a.name LIKE ?;
            """;

    private static final String FIND_ALL_ACTORS_BY_MOVIE_ID = FIND_ALL_ACTORS + """
                     LEFT JOIN actor_movie am ON a.id = am.actor_id
                     LEFT JOIN movie m ON am.movie_id = m.id
            WHERE m.id = ?;
            """;

    private static final String FIND_ACTOR_BY_ID = FIND_ALL_ACTORS + """
            WHERE a.id = ?
            """;

    private static final String SAVE_ACTOR = """
            INSERT INTO actor (name, date_of_birth) VALUES (?, ?);
            """;

    private static final String UPDATE_ACTOR = """
            UPDATE actor SET name = ?, date_of_birth = ? WHERE id = ?;
            """;

    private static final String DELETE_ACTOR_BY_ID = """
            DELETE FROM actor
            WHERE actor.id = ?;
            """;

    private static final String DELETE_ACTOR_FROM_ACTOR_MOVIE = """
            DELETE FROM actor_movie
            WHERE actor_id =?;
            """;

    @Override
    @SneakyThrows
    public List<Actor> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_ACTORS)) {
            return getActors(preparedStatement);
        }
    }

    @SneakyThrows
    public List<Actor> findActorsByPrefix(String prefix) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ACTORS_BY_PREFIX)) {
            preparedStatement.setObject(1, prefix + "%");
            return getActors(preparedStatement);
        }
    }

    @Override
    @SneakyThrows
    public Optional<Actor> findById(Integer actorId) {
        try (Connection connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ACTOR_BY_ID)) {
            preparedStatement.setObject(1, actorId);
            var resultSet = preparedStatement.executeQuery();
            Actor actor = null;
            if (resultSet.next()) {
                actor = buildActor(resultSet);
            }
            return Optional.ofNullable(actor);
        }
    }

    @Override
    @SneakyThrows
    public Actor save(Actor entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_ACTOR, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDateOfBirthday());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        }
    }

    @Override
    @SneakyThrows
    public void update(Actor entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_ACTOR)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDateOfBirthday());
            preparedStatement.setObject(3, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_ACTOR_BY_ID)) {
            preparedStatement.setObject(1, id);
            deleteActorFromActorMovie(id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public List<Actor> findAllActorByMovieId(Integer movieId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_ACTORS_BY_MOVIE_ID)) {
            preparedStatement.setObject(1, movieId);
            return getActors(preparedStatement);
        }
    }

    @SneakyThrows
    private void deleteActorFromActorMovie(Integer actorId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_ACTOR_FROM_ACTOR_MOVIE)) {
            preparedStatement.setObject(1, actorId);
            preparedStatement.executeUpdate();
        }
    }

    private List<Actor> getActors(PreparedStatement preparedStatement) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            actors.add(buildActor(resultSet));
        }
        return actors;
    }

    private Actor buildActor(ResultSet resultSet) throws SQLException {
        return Actor.builder()
                .id(resultSet.getObject("actor_id", Integer.class))
                .name(resultSet.getObject("actor_name", String.class))
                .dateOfBirthday(resultSet.getObject("actor_date_of_birth", Date.class).toLocalDate())
                .build();
    }

    public static ActorDao getInstance() {
        return INSTANCE;
    }
}

