package ru.zharinov.dao;

import lombok.SneakyThrows;
import ru.zharinov.entity.Feedback;
import ru.zharinov.entity.Movie;
import ru.zharinov.entity.User;
import ru.zharinov.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

public class FeedbackDao implements Dao<Integer, Feedback> {

    private static final String FIND_ALL_FEEDBACK = """
            SELECT f.id, f.text, f.assessment, m.name AS movie_name, u.name AS user_name
            FROM feedback f
            LEFT JOIN movie m ON m.id = f.movie_id
            LEFT JOIN users u  ON f.user_id = u.id
            """;

    private static final String FIND_ALL_FEEDBACK_BY_MOVIE_ID = FIND_ALL_FEEDBACK + """            
            WHERE movie_id = ?;
            """;

    private static final String FIND_ALL_FEEDBACK_BY_USER_ID = FIND_ALL_FEEDBACK + """            
            WHERE u.id = ?;
            """;

    private static final String SAVE_FEEDBACK = """
            INSERT INTO feedback (text, assessment, movie_id, user_id) VALUES (?, ?, ?, ?);
            """;

    public static final String DELETE_FEEDBACK = """
            DELETE FROM feedback
            WHERE id = ?;
            """;


    @Override
    public List<Feedback> findAll() {
        return List.of();
    }

    @SneakyThrows
    public List<Feedback> findAllFeedbackByMovieId(Integer movieId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_FEEDBACK_BY_MOVIE_ID)) {
            preparedStatement.setObject(1, movieId);
            return getFeedbacks(preparedStatement);
        }
    }

    @SneakyThrows
    public List<Feedback> findAllFeedbackByUserId(Integer userId) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_FEEDBACK_BY_USER_ID)) {
            preparedStatement.setObject(1, userId);
            return getFeedbacks(preparedStatement);
        }
    }

    @Override
    public Optional<Feedback> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Feedback save(Feedback entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_FEEDBACK, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getText());
            preparedStatement.setObject(2, entity.getAssessment());
            preparedStatement.setObject(3, entity.getMovie().getId());
            preparedStatement.setObject(4, entity.getUser().getId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        }
    }

    @Override
    public void update(Feedback entity) {

    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_FEEDBACK)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private static List<Feedback> getFeedbacks(PreparedStatement preparedStatement) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            feedbacks.add(buildFeedback(resultSet));
        }
        return feedbacks;
    }

    private static Feedback buildFeedback(ResultSet resultSet) throws SQLException {
        var movie = Movie.builder()
                .name(resultSet.getObject("movie_name", String.class)).build();

        var userName = User.builder().name(resultSet.getObject("user_name", String.class)).build();

        return Feedback.builder()
                .id(resultSet.getObject("id", Integer.class))
                .text(resultSet.getObject("text", String.class))
                .assessment(resultSet.getObject("assessment", Integer.class))
                .movie(movie)
                .user(userName)
                .build();
    }
}
