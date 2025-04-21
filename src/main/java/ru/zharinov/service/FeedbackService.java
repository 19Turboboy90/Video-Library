package ru.zharinov.service;

import lombok.Setter;
import ru.zharinov.dao.FeedbackDao;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.mapper.FeedbackMapper;
import ru.zharinov.validation.EntityValidator;

import java.util.List;
import java.util.Optional;

public class FeedbackService {
    private final FeedbackDao feedbackDao;
    @Setter
    private UserService userService;
    @Setter
    private MovieService movieService;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public List<Feedback> findAllFeedbackByMovieId(Integer movieId) {
        return feedbackDao.findAllFeedbackByMovieId(movieId);
    }

    public List<Feedback> findAllFeedbackByUserId(Integer userId) {
        return feedbackDao.findAllFeedbackByUserId(userId);
    }

    public Feedback save(CreateFeedbackDto createFeedbackDto) {
        var movieById =
                movieService.findById(Integer.parseInt(createFeedbackDto.getMovieId()));
        System.out.println(Optional.ofNullable(movieService));
        EntityValidator.validateEntityExists(movieById, createFeedbackDto.getMovieId(), "movie");
        var userById =
                userService.findById(Integer.parseInt(createFeedbackDto.getUserId()));
        EntityValidator.validateEntityExists(userById, createFeedbackDto.getUserId(), "user");
        var feedback = FeedbackMapper.toFeedback(createFeedbackDto);

        feedback.setMovie(movieById.orElseThrow());
        feedback.setUser(userById.orElseThrow());
        return feedbackDao.save(feedback);
    }

    public boolean deleteFeedbackById(Integer id) {
        EntityValidator.validateId(id, "feedbackId");
        return feedbackDao.delete(id);
    }
}
