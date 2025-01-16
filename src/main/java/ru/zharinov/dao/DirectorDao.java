package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Director;
import ru.zharinov.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorDao implements Dao<Integer, Director> {
    private static final DirectorDao INSTANCE = new DirectorDao();

    private static final String FIND_ALL_DIRECTORS = """
            SELECT d.id, d.name, d.date_of_birth
            FROM director d
            """;

    private static final String FIND_DIRECTOR_BY_MOVIE_ID = FIND_ALL_DIRECTORS + """
            JOIN movie m ON d.id = m.director_id
            WHERE m.id = ?;
            """;

    private static final String FIND_DIRECTOR_BY_ID = FIND_ALL_DIRECTORS + """
            WHERE d.id = ?;
            """;

    @Override
    public List<Director> findAll() {
        return List.of();
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

    private Director builddirector(ResultSet resultSet) throws SQLException {
        return Director.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .dateOfBirthday(resultSet.getObject("date_of_birth", Date.class).toLocalDate())
                .build();
    }

    public static DirectorDao getInstance() {
        return INSTANCE;
    }
}
