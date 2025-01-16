package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Movie;
import ru.zharinov.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieDao implements Dao<Integer, Movie> {
    private static final MovieDao INSTANCE = new MovieDao();

    private static final String FIND_ALL_MOVIES = """
            SELECT m.id,
                   m.name,
                   m.premiere_date,
                   m.country,
                   m.genre
            FROM movie m
            """;

    private static final String FIND_MOVIE_BY_ID = FIND_ALL_MOVIES + """
            WHERE m.id = ?
            """;

    private static final String FIND_ALL_MOVIES_BY_ACTOR_ID = FIND_ALL_MOVIES + """
            LEFT JOIN actor_movie am ON m.id = am.movie_id
            LEFT JOIN actor a ON am.actor_id = a.id
            WHERE a.id = ?
            """;

    private static final String FIND_ALL_MOVIES_BY_DIRECTOR_ID = FIND_ALL_MOVIES + """
            JOIN director d on d.id = m.director_id
            WHERE d.id = ?;
            """;


    @Override
    @SneakyThrows
    public List<Movie> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_MOVIES)) {
            var resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(buildMovie(resultSet));
            }
            return movies;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Movie> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_MOVIE_BY_ID)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            Movie movie = null;
            if (resultSet.next()) {
                movie = buildMovie(resultSet);
            }
            return Optional.ofNullable(movie);
        }
    }

    public List<Movie> findAllMovieByActorId(Integer actorId) {
        return findAllMovieById(actorId, FIND_ALL_MOVIES_BY_ACTOR_ID);
    }

    public List<Movie> findAllMoviesByDirectorId(Integer directorId) {
        return findAllMovieById(directorId, FIND_ALL_MOVIES_BY_DIRECTOR_ID);
    }

    @SneakyThrows
    private List<Movie> findAllMovieById(Integer actorId, String sql) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, actorId);
            var resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(buildMovie(resultSet));
            }
            return movies;
        }
    }

    @Override
    public Movie save(Movie entity) {
        return null;
    }

    @Override
    public void update(Movie entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private Movie buildMovie(ResultSet resultSet) throws SQLException {
        return Movie.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .premierDate(resultSet.getObject("premiere_date", Date.class).toLocalDate())
                .country(resultSet.getObject("country", String.class))
                .genre(resultSet.getObject("genre", String.class))
                .build();
    }

    public static MovieDao getInstance() {
        return INSTANCE;
    }
}

