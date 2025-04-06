package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.*;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FactoryService {
    private static final FactoryService INSTANCE = new FactoryService();
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;
    private UserService userService;
    private FeedbackService feedbackService;

    private final ActorDao actorDao = new ActorDao();
    private final DirectorDao directorDao = new DirectorDao();
    private final FeedbackDao feedbackDao = new FeedbackDao();
    private final MovieDao movieDao = new MovieDao();
    private final UserDao userDao = new UserDao();

    public MovieService getMovieService() {
        if (movieService == null) {
            movieService = new MovieService(this, movieDao);
        }
        return movieService;
    }

    public ActorService getActorService() {
        if (actorService == null) {
            actorService = new ActorService(this, actorDao);
        }
        return actorService;
    }

    public DirectorService getDirectorService() {
        if (directorService == null) {
            directorService = new DirectorService(this, directorDao);
        }
        return directorService;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService(userDao);
        }
        return userService;
    }

    public FeedbackService getFeedbackService() {
        if (feedbackService == null) {
            feedbackService = new FeedbackService(feedbackDao);
        }
        return feedbackService;
    }

    public static FactoryService getInstance() {
        return INSTANCE;
    }
}
