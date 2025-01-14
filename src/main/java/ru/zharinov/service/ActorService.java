package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.ActorDto;
import ru.zharinov.exception.CreateNotFoundException;
import ru.zharinov.mapper.ActorMapper;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorService {
    private static final ActorService INSTANCE = new ActorService();
    private final ActorDao actorDao = ActorDao.getInstance();
    private final ActorMapper actorMapper = ActorMapper.getInstance();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final CreateNotFoundException exception = new CreateNotFoundException();

    public Optional<ActorDto> findActorByMovieId(Integer id) {
        var actor = actorDao.findById(id);
        if (actor.isPresent()) {
            var allMovieByActorId = movieDao.findAllMovieByActorId(actor.get().getId());
            actor.get().setMovies(allMovieByActorId);
        } else {
            throw new RuntimeException("ID is not found = " + id);
        }
        return actor.map(actorMapper::mapper);
    }


    public static ActorService getInstance() {
        return INSTANCE;
    }

}
