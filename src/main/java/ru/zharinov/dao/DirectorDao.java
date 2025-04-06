package ru.zharinov.dao;

import lombok.SneakyThrows;
import ru.zharinov.entity.Director;
import ru.zharinov.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

public class DirectorDao implements Dao<Integer, Director> {

    private static final String FIND_ALL_DIRECTORS = """
            SELECT d.id, d.name, d.date_of_birth
            FROM director d
            """;

    private static final String FIND_DIRECTORS_BY_PREFIX = FIND_ALL_DIRECTORS + """
            WHERE d.name LIKE ?;
            """;

    private static final String FIND_DIRECTOR_BY_MOVIE_ID = FIND_ALL_DIRECTORS + """
            JOIN movie m ON d.id = m.director_id
            WHERE m.id = ?;
            """;

    private static final String FIND_DIRECTOR_BY_ID = FIND_ALL_DIRECTORS + """
            WHERE d.id = ?;
            """;

    private static final String SAVE_DIRECTOR = """
            INSERT INTO director (name, date_of_birth) VALUES (?, ?);
            """;

    private static final String UPDATE_DIRECTOR = """
            UPDATE director SET name = ?, date_of_birth = ? WHERE id = ?;
            """;

    private static final String DELETE_DIRECTOR = """
            DELETE FROM director WHERE id = ?;
            """;

    @Override
    @SneakyThrows
    public List<Director> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_DIRECTORS)) {
            var resultSet = preparedStatement.executeQuery();
            return getDirectors(resultSet);
        }
    }

    @SneakyThrows
    public List<Director> finDirectorsByPrefix(String prefix) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_DIRECTORS_BY_PREFIX)) {
            preparedStatement.setObject(1, prefix + "%");
            var resultSet = preparedStatement.executeQuery();
            return getDirectors(resultSet);
        }
    }

    @Override
    public Optional<Director> findById(Integer directorId) {
        return findDirectorById(directorId, FIND_DIRECTOR_BY_ID);
    }

    public Optional<Director> findDirectorByMovieId(Integer movieId) {
        return findDirectorById(movieId, FIND_DIRECTOR_BY_MOVIE_ID);
    }

    @SneakyThrows
    private Optional<Director> findDirectorById(Integer movieId, String sql) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, movieId);
            var resultSet = preparedStatement.executeQuery();
            Director director = null;
            if (resultSet.next()) {
                director = builddirector(resultSet);
            }
            return Optional.ofNullable(director);
        }
    }

    @Override
    @SneakyThrows
    public Director save(Director entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_DIRECTOR, RETURN_GENERATED_KEYS)) {
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
    public void update(Director entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_DIRECTOR)) {
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
             var preparedStatement = connection.prepareStatement(DELETE_DIRECTOR)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private List<Director> getDirectors(ResultSet resultSet) throws SQLException {
        List<Director> directors = new ArrayList<>();
        while (resultSet.next()) {
            directors.add(builddirector(resultSet));
        }
        return directors;
    }

    private Director builddirector(ResultSet resultSet) throws SQLException {
        return Director.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .dateOfBirthday(resultSet.getObject("date_of_birth", Date.class).toLocalDate())
                .build();
    }
}
