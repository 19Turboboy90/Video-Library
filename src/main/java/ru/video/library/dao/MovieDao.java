package main.java.ru.video.library.dao;

import main.java.ru.video.library.entity.Movie;
import main.java.ru.video.library.exception.DaoException;
import main.java.ru.video.library.sql.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MovieDao implements StorageDao<Integer, Movie> {
    private static final MovieDao INSTANCE = new MovieDao();

    private MovieDao() {
    }

    public static MovieDao getInstance() {
        return INSTANCE;
    }

    private static final String DELETE_MOVIE_SQL = """
            DELETE FROM movie
            WHERE movie.id = ?
            """;

    private static final String UPDATE_MOVIE_SQL = """
            UPDATE movie
            SET name = ?,
                premiere_date = ?,
                country = ?,
                genre = ?
            WHERE movie.id = ?
            """;

    private static final String SAVE_MOVIE_SQL = """
            INSERT INTO movie (name, director_id, premiere_date, country, genre)
            VALUES (?,?,?,?,?);
            """;

    private static final String GET_MOVIE_BY_ID_SQL = """
            SELECT movie.id,
                   movie.name,
                   director_id,
                   premiere_date,
                   country,
                   genre,
                   a.name,
                   a.date_of_birth,
                   a.movie_id,
                   d.name,
                   d.date_of_birth
            FROM movie
                     JOIN actor a
                     ON movie.id = a.movie_id
                     JOIN director d
                     ON movie.id = d.id
            WHERE movie.id = ?;
            """;

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(DELETE_MOVIE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Movie save(Movie movie) {
        Connection connection = null;
        try {
            connection = ConnectionManager.open();
            var preparedStatement =
                    connection.prepareStatement(SAVE_MOVIE_SQL, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);

            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getDirector().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(movie.getPremiereDate().atStartOfDay()));
            preparedStatement.setString(4, movie.getCountry());
            preparedStatement.setString(5, movie.getGenre());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movie.setId(generatedKeys.getInt("id"));
            }
            return movie;
//            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Movie movie) {
        Connection connection = null;
        try {
            connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(UPDATE_MOVIE_SQL);
            connection.setAutoCommit(false);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(movie.getPremiereDate().atStartOfDay()));
            preparedStatement.setString(3, movie.getName());
            preparedStatement.setString(4, movie.getName());
            preparedStatement.setInt(5, movie.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(GET_MOVIE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            Movie movie = null;

            if (resultSet.next()) {

            }
            return Optional.ofNullable(movie);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }

//    private Movie buildTicket(ResultSet resultSet) throws SQLException {
//        var actor = new Actor(
//                resultSet.getInt("id"),
//                resultSet.getString("name"),
//                resultSet.getDate("date_ff_birth").toLocalDate()
//        );
//
//        var director = new Director(
//                resultSet.getInt("id"),
//                resultSet.getString("name"),
//                resultSet.getDate("date_ff_birth").toLocalDate()
//        );
//
//        return new Movie(
//                resultSet.getInt("id"),
//                resultSet.getString("name"),
//                actorDao.findById(actor.getId()).orElse(null),
//                directorDao.findById(director.getId()).orElse(null),
//                resultSet.getDate("premiere_date"),
//                resultSet.getString("country"),
//                resultSet.getString("genre")
//        );
//    }
}
