package ru.zharinov.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.entity.Actor;
import ru.zharinov.entity.Director;
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
    private final ActorDao actorDao = ActorDao.getInstance();

    private static final String FIND_ALL_MOVIES = """
            SELECT m.id,
                   m.name,
                   m.premiere_date,
                   m.country,
                   m.genre
            FROM movie m
            """;

    private static final String FIND_MOVIE_BY_ID = """
            SELECT m.id AS movie_id,
                   m.name AS movie_name,
                   m.premiere_date AS movie_premiere_date,
                   m.country AS movie_country,
                   m.genre AS movie_genre,
                   d.id AS director_id,
                   d.name AS director_name,
                   d.date_of_birth AS director_date_of_birth
            FROM movie m
                     LEFT JOIN director d ON m.director_id = d.id
            WHERE m.id = ?
            """;

    private static final String FIND_ALL_MOVIES_BY_ACTOR_ID = FIND_ALL_MOVIES + """
            LEFT JOIN actor_movie am ON m.id = am.movie_id
            LEFT JOIN actor a ON am.actor_id = a.id
            WHERE a.id = ?
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
                movie = buildMovieAllInformation(resultSet);
            }
            return Optional.ofNullable(movie);
        }
    }

    @SneakyThrows
    public List<Movie> findAllMovieByActorId(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_MOVIES_BY_ACTOR_ID)) {
            preparedStatement.setObject(1, id);
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

    private Movie buildMovieAllInformation(ResultSet resultSet) throws SQLException {
        var movie = Movie.builder()
                .id(resultSet.getObject("movie_id", Integer.class))
                .name(resultSet.getObject("movie_name", String.class))
                .premierDate(resultSet.getObject("movie_premiere_date", Date.class).toLocalDate())
                .country(resultSet.getObject("movie_country", String.class))
                .genre(resultSet.getObject("movie_genre", String.class))
                .build();

        Director director = Director.builder()
                .id(resultSet.getObject("director_id", Integer.class))
                .name(resultSet.getObject("director_name", String.class))
                .dateOfBirthday(resultSet.getObject("director_date_of_birth", Date.class).toLocalDate())
                .build();

        movie.setDirector(director);

        List<Actor> actors = actorDao.findAllActorByMovieId(movie.getId());
        movie.setActors(actors);

        return movie;
    }

    public static MovieDao getInstance() {
        return INSTANCE;
    }
}

