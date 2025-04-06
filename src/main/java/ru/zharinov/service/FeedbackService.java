package ru.zharinov.service;

import ru.zharinov.dao.FeedbackDao;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.mapper.FeedbackMapper;
import ru.zharinov.validation.EntityValidator;

import java.util.List;

public class FeedbackService {
    private final FeedbackDao feedbackDao;
    private final FactoryService factoryService = FactoryService.getInstance();

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public List<Feedback> findAllFeedbackByMovieId(Integer movieId) {
        return feedbackDao.findAllFeedbackByMovieId(movieId);
    }

    public List<Feedback> findAllFeedbackByUserId(Integer userId) {
        return feedbackDao.findAllFeedbackByUserId(userId);
    }

    public void save(CreateFeedbackDto createFeedbackDto) {
        var movieById =
                factoryService.getMovieService().findById(Integer.parseInt(createFeedbackDto.getMovieId()));
        EntityValidator.validateEntityExists(movieById, createFeedbackDto.getMovieId(), "movie");
        var userById = factoryService.getUserService().findById(Integer.parseInt(createFeedbackDto.getUserId()));
        EntityValidator.validateEntityExists(userById, createFeedbackDto.getUserId(), "user");
        var feedback = FeedbackMapper.toFeedback(createFeedbackDto);

        feedback.setMovie(movieById.orElseThrow());
        feedback.setUser(userById.orElseThrow());
        feedbackDao.save(feedback);
    }

    public void deleteFeedbackById(Integer id) {
        EntityValidator.validateId(id, "feedbackId");
        feedbackDao.delete(id);
    }
}
