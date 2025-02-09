package ru.zharinov.service;

import ru.zharinov.dao.ActorDao;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.actor.ActorMapper;
import ru.zharinov.mapper.actor.ActorWithMoviesMapper;
import ru.zharinov.mapper.actor.CreateOrUpdateActorMapper;
import ru.zharinov.validation.ActorValidator;
import ru.zharinov.validation.EntityValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ActorService {
    private final FactoryService factoryService;
    private final ActorDao actorDao = ActorDao.getInstance();
    private final ActorValidator actorValidator = ActorValidator.getInstance();
    private final ActorWithMoviesMapper actorWithMoviesMapper = ActorWithMoviesMapper.getInstance();
    private final CreateOrUpdateActorMapper createOrUpdateActorMapper = CreateOrUpdateActorMapper.getInstance();
    private final ActorMapper actorMapper = ActorMapper.getInstance();

    public ActorService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public Optional<ActorDto> findActorById(Integer actorId) {
        EntityValidator.validateId(actorId, "actor");
        var actor = actorDao.findById(actorId);
        EntityValidator.validateEntityExists(actor, actorId, "actor");
        var allMovieByActorId = factoryService.getMovieService().findAllMovieByActorId(actorId);
        actor.ifPresent(a -> a.setMovies(allMovieByActorId));

        return actor.map(actorWithMoviesMapper::mapper);
    }

    public List<Actor> findAllActorByMovieId(Integer movieId) {
        EntityValidator.validateId(movieId, "movie");
        return Optional.ofNullable(actorDao.findAllActorByMovieId(movieId)).orElse(Collections.emptyList());
    }

    public List<ActorDto> findAllActor() {
        return Optional.of(actorDao.findAll().stream().map(actorMapper::mapper).toList()).orElse(Collections.emptyList());
    }

    public List<ActorDto> findActorsByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return actorDao.findActorsByPrefix(param).stream().map(actorMapper::mapper).toList();
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
}
