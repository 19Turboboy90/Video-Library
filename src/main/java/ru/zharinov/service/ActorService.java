package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.ActorDto;
import ru.zharinov.mapper.ActorWithMoviesMapper;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorService {
    private static final ActorService INSTANCE = new ActorService();
    private final ActorDao actorDao = ActorDao.getInstance();
    private final ActorWithMoviesMapper actorMapper = ActorWithMoviesMapper.getInstance();
    private final MovieDao movieDao = MovieDao.getInstance();

    public Optional<ActorDto> findActorById(Integer actorId) {
        var actor = actorDao.findById(actorId);
        if (actor.isPresent()) {
            var allMovieByActorId = movieDao.findAllMovieByActorId(actor.get().getId());
            actor.get().setMovies(allMovieByActorId);
        } else {
            throw new RuntimeException("ID is not found = " + actorId);
        }
        return actor.map(actorMapper::mapper);
    }


    public static ActorService getInstance() {
        return INSTANCE;
    }

}
