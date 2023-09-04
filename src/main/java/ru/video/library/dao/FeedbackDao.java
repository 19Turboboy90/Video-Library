package main.java.ru.video.library.dao;

public class FeedbackDao {
    private static final FeedbackDao FEEDBACK_DAO = new FeedbackDao();

    private FeedbackDao() {
    }

    public static FeedbackDao getInstance() {
        return FEEDBACK_DAO;
    }
}
