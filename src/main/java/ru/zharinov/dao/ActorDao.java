package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Actor;
import ru.zharinov.util.ConnectionManager;

import java.sql.Connection;
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

    private static final String FIND_ALL_ACTORS_BY_MOVIE_ID = """
            SELECT a.id AS actor_id,
                   a.name AS actor_name,
                   a.date_of_birth AS actor_date_of_birth
            FROM actor a
                     LEFT JOIN actor_movie am ON a.id = am.actor_id
                     LEFT JOIN movie m ON am.movie_id = m.id
            WHERE m.id = ?;
            """;

    private static final String FIND_ACTOR_BY_ID = """
            SELECT a.id AS actor_id,
                   a.name AS actor_name,
                   a.date_of_birth AS actor_date_of_birth
            FROM actor a
            WHERE a.id = ?
            """;

    @Override
    public List<Actor> findAll() {
        return List.of();
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
             var preparedStatement = connection.prepareStatement(FIND_ALL_ACTORS_BY_MOVIE_ID)) {
            preparedStatement.setObject(1, movieId);
            List<Actor> actors = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actors.add(buildActor(resultSet));
            }
            return actors;
        }
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

