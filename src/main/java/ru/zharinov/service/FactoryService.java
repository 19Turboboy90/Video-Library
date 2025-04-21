package ru.zharinov.service;

import lombok.Getter;
import ru.zharinov.dao.*;

public class FactoryService {
    private static final FactoryService INSTANCE = new FactoryService();

    private final ActorDao actorDao = new ActorDao();
    private final DirectorDao directorDao = new DirectorDao();
    private final FeedbackDao feedbackDao = new FeedbackDao();
    private final MovieDao movieDao = new MovieDao();
    private final UserDao userDao = new UserDao();

    @Getter
    private final MovieService movieService = new MovieService(movieDao);
    @Getter
    private final ActorService actorService = new ActorService(actorDao);
    @Getter
    private final DirectorService directorService = new DirectorService(directorDao);
    @Getter
    private final FeedbackService feedbackService = new FeedbackService(feedbackDao);
    @Getter
    private final UserService userService = new UserService(userDao);

    private FactoryService() {
        movieService.setActorService(actorService);
        movieService.setDirectorService(directorService);

        actorService.setMovieService(movieService);
        directorService.setMovieService(movieService);

        feedbackService.setMovieService(movieService);
        feedbackService.setUserService(userService);

        userService.setFeedbackService(feedbackService);
    }

    public static FactoryService getInstance() {
        return INSTANCE;
    }
}

