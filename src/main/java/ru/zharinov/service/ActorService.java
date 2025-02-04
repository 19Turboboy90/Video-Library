package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.actor.ActorMapper;
import ru.zharinov.mapper.actor.ActorWithMoviesMapper;
import ru.zharinov.mapper.actor.CreateOrUpdateActorMapper;
import ru.zharinov.validation.ActorValidator;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorService {
    private static final ActorService INSTANCE = new ActorService();
    private final ActorDao actorDao = ActorDao.getInstance();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final ActorValidator actorValidator = ActorValidator.getInstance();
    private final ActorWithMoviesMapper actorWithMoviesMapper = ActorWithMoviesMapper.getInstance();
    private final CreateOrUpdateActorMapper createOrUpdateActorMapper = CreateOrUpdateActorMapper.getInstance();
    private final ActorMapper actorMapper = ActorMapper.getInstance();

    public Optional<ActorDto> findActorById(Integer actorId) {
        var actor = actorDao.findById(actorId);
        if (actor.isPresent()) {
            var allMovieByActorId = movieDao.findAllMovieByActorId(actor.get().getId());
            actor.get().setMovies(allMovieByActorId);
        } else {
            throw new RuntimeException("ID is not found = " + actorId);
        }
        return actor.map(actorWithMoviesMapper::mapper);
    }

    public List<ActorDto> findAllActor() {
        return actorDao.findAll().stream().map(actorMapper::mapper).toList();
    }

    public List<ActorDto> findActorsByPrefix(String prefix) {
        return actorDao.findActorsByPrefix(prefix).stream().map(actorMapper::mapper).toList();
    }

    public void save(CreateOrUpdateActorDto createActorDto) {
        var valid = actorValidator.isValid(createActorDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        if (createActorDto.getId() == null || createActorDto.getId().isBlank()) {
            actorDao.save(createOrUpdateActorMapper.mapper(createActorDto));
        } else {
            actorDao.update(createOrUpdateActorMapper.mapper(createActorDto));
        }
    }

    public void delete(Integer actorId) {
        actorDao.delete(actorId);
    }

    public static ActorService getInstance() {
        return INSTANCE;
    }
}
