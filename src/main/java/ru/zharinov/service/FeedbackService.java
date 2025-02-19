package ru.zharinov.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zharinov.dao.FeedbackDao;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.mapper.feedback.CreateFeedbackMapper;
import ru.zharinov.validation.EntityValidator;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackService {
    private final FeedbackDao feedbackDao = FeedbackDao.getInstance();
    private static final FeedbackService INSTANCE = new FeedbackService();
    private final FactoryService factoryService = FactoryService.getInstance();
    private final CreateFeedbackMapper createFeedbackMapper = CreateFeedbackMapper.getInstance();

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
        var feedback = createFeedbackMapper.mapper(createFeedbackDto);

        feedback.setMovie(movieById.orElseThrow());
        feedback.setUser(userById.orElseThrow());
        feedbackDao.save(feedback);
    }

    public void deleteFeedbackById(Integer id) {
        EntityValidator.validateId(id, "feedbackId");
        feedbackDao.delete(id);
    }

    public static FeedbackService getInstance() {
        return INSTANCE;
    }
}
