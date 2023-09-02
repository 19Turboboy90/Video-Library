package main.java.ru.video.library.dao;

import main.java.ru.video.library.entity.Movie;
import main.java.ru.video.library.exception.DaoException;
import main.java.ru.video.library.sql.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MovieDao implements StorageDao<Integer, Movie> {
    private static final String DELETE_MOVIE = """
            DELETE FROM movie
            WHERE movie.id = ?
            """;

    private static final String UPDATE_MOVIE = """
            UPDATE movie
            SET name = ?,
                premiere_date = ?,
                country = ?,
                genre = ?
            WHERE movie.id = ?
            """;

    private static final String SAVE_MOVIE = """
                        
            """;

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(DELETE_MOVIE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Movie save(Movie movie) {
        return null;
    }

    @Override
    public void update(Movie movie) {
        Connection connection = null;
        try {
            connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(UPDATE_MOVIE);
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
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }
}
