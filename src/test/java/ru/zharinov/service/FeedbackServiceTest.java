package ru.zharinov.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zharinov.dao.FeedbackDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.entity.Movie;
import ru.zharinov.entity.Role;
import ru.zharinov.entity.User;
import ru.zharinov.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    private static final Movie MOVIE = new Movie(1, "TestMovie", LocalDate.now(),
            "testCountry", "testGender", null, null, null);
    private static final User USER =
            new User(1, "testUser", "testEmail@mail.ru", "111", Role.USER, List.of());

    private static final Feedback FEEDBACK_1 = new Feedback(1, "testFeedback1", 5, MOVIE, USER);
    private static final Feedback FEEDBACK_2 = new Feedback(2, "testFeedback2", 10, null, null);

    @Mock
    private FeedbackDao feedbackDao;
    @Mock
    private MovieDao movieDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private FeedbackService feedbackService;
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        feedbackService.setMovieService(movieService);
        feedbackService.setUserService(userService);
    }

    @Test
    void testServiceInjection() {
        assertThat(movieService).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    void findAllFeedbackByMovieId() {
        when(feedbackDao.findAllFeedbackByMovieId(MOVIE.getId())).thenReturn(List.of(FEEDBACK_1));

        var allFeedbackByMovieId = feedbackService.findAllFeedbackByMovieId(MOVIE.getId());

        assertThat(allFeedbackByMovieId).hasSize(1).isEqualTo(List.of(FEEDBACK_1));
    }

    @Test
    void movieIdIsNotFound() {
        when(feedbackDao.findAllFeedbackByMovieId(-1)).thenReturn(List.of());

        var allFeedbackByMovieId = feedbackService.findAllFeedbackByMovieId(-1);

        assertThat(allFeedbackByMovieId).hasSize(0).isEqualTo(List.of());
    }

    @Test
    void findAllFeedbackByUserId() {
        when(feedbackDao.findAllFeedbackByUserId(USER.getId())).thenReturn(List.of(FEEDBACK_2));

        var allFeedbackByUserId = feedbackService.findAllFeedbackByUserId(USER.getId());

        assertThat(allFeedbackByUserId).hasSize(1).isEqualTo(List.of(FEEDBACK_2));
    }


    @Test
    void userIdIsNotFound() {
        when(feedbackDao.findAllFeedbackByUserId(-1)).thenReturn(List.of());

        var allFeedbackByUserId = feedbackService.findAllFeedbackByUserId(-1);

        assertThat(allFeedbackByUserId).hasSize(0).isEqualTo(List.of());
    }

    @Test
    void save() {
        when(movieDao.findById(MOVIE.getId())).thenReturn(Optional.of(MOVIE));
        when(userDao.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(feedbackDao.save(any(Feedback.class))).thenReturn(FEEDBACK_2);

        var movie = movieService.findById(MOVIE.getId());
        var user = userService.findById(USER.getId());
        FEEDBACK_2.setMovie(movie.orElseThrow());
        FEEDBACK_2.setUser(user.orElseThrow());

        var feedback = feedbackService.save(buildCreateFeedbackDto());

        assertThat(feedback).isEqualTo(FEEDBACK_2);
    }

    @Test
    void deleteFeedbackById() {
        when(feedbackDao.delete(FEEDBACK_1.getId())).thenReturn(true);

        var result = feedbackService.deleteFeedbackById(FEEDBACK_1.getId());

        assertThat(result).isTrue();

        verify(feedbackDao).delete(FEEDBACK_1.getId());
    }

    @Test
    void deleteIsNotFeedback() {
        when(feedbackDao.delete(-1)).thenReturn(false);

        var result = feedbackService.deleteFeedbackById(-1);

        assertThat(result).isFalse();

        verify(feedbackDao).delete(-1);
    }

    @Test
    void checkFeedbackId() {
        assertThrows(NotFoundException.class, () -> feedbackService.deleteFeedbackById(null));
    }

    private CreateFeedbackDto buildCreateFeedbackDto() {
        return CreateFeedbackDto.builder()
//                .userId(String.valueOf(FeedbackServiceTest.FEEDBACK_2.getId()))
                .text(FeedbackServiceTest.FEEDBACK_2.getText())
                .assessment(String.valueOf(FeedbackServiceTest.FEEDBACK_2.getAssessment()))
                .userId(String.valueOf(FeedbackServiceTest.FEEDBACK_2.getUser().getId()))
                .movieId(String.valueOf(FeedbackServiceTest.FEEDBACK_2.getMovie().getId()))
                .build();
    }
}