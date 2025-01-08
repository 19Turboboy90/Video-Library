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
    private final ActorDao actorDao = ActorDao.getInstance();

//    private static final String FIND_ALL_MOVIES = """
//            SELECT m.id,
//                   m.name,
//                   m.premiere_date,
//                   m.country,
//                   m.genre,
//                   m.director_id,
//                   d.id,
//                   d.name,
//                   d.date_of_birth
//            FROM movie m
//                     LEFT JOIN director d ON m.director_id = d.id
//            """;

    private static final String FIND_ALL_MOVIES = """
            SELECT id,
                   name,
                   premiere_date,
                   country,
                   genre
            FROM movie
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
    public Optional<Movie> findById(Integer id) {
        return Optional.empty();
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
//        Director director = Director.builder()
//                .id(resultSet.getObject("d.id", Integer.class))
//                .name(resultSet.getObject("d.name", String.class))
//                .dateOfBirthday(resultSet.getObject("d.date_of_birth", Date.class).toLocalDate())
//                .build();


        return Movie.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .premierDate(resultSet.getObject("premiere_date", Date.class).toLocalDate())
                .country(resultSet.getObject("country", String.class))
                .genre(resultSet.getObject("genre", String.class))
//                .director(director)
                .build();
    }

    public static MovieDao getInstance() {
        return INSTANCE;
    }
}

