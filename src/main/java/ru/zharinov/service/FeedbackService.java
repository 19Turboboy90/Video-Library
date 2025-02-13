package ru.zharinov.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zharinov.dao.FeedbackDao;
import ru.zharinov.entity.Feedback;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackService {
    private final FeedbackDao feedbackDao = FeedbackDao.getInstance();
    private static final FeedbackService INSTANCE = new FeedbackService();

    public List<Feedback> findAllFeedbackByMovieId(Integer movieId) {
        return feedbackDao.findAllFeedbackByMovieId(movieId);
    }

    public List<Feedback> findAllFeedbackByUserId(Integer userId) {
        return feedbackDao.findAllFeedbackByUserId(userId);
    }

    public static FeedbackService getInstance() {
        return INSTANCE;
    }
}
