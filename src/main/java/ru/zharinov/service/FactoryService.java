package ru.zharinov.service;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FactoryService {
    private static final FactoryService INSTANCE = new FactoryService();
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;

    public MovieService getMovieService() {
        if (movieService == null) {
            movieService = new MovieService(this);
        }
        return movieService;
    }

    public ActorService getActorService() {
        if (actorService == null) {
            actorService = new ActorService(this);
        }
        return actorService;
    }

    public DirectorService getDirectorService() {
        if (directorService == null) {
            directorService = new DirectorService(this);
        }
        return directorService;
    }


    public static FactoryService getInstance() {
        return INSTANCE;
    }
}
