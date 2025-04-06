package ru.zharinov.service;

import ru.zharinov.dao.ActorDao;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.ActorMapper;
import ru.zharinov.validation.ActorValidator;
import ru.zharinov.validation.EntityValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActorService {
    private final FactoryService factoryService;
    private final ActorDao actorDao;
    private final ActorValidator actorValidator = ActorValidator.getInstance();

    public ActorService(FactoryService factoryService, ActorDao actorDao) {
        this.factoryService = factoryService;
        this.actorDao = actorDao;
    }

    public Optional<ActorDto> findActorById(Integer actorId) {
        EntityValidator.validateId(actorId, "actor");
        var actor = actorDao.findById(actorId);
        EntityValidator.validateEntityExists(actor, actorId, "actor");
        var allMovieByActorId = factoryService.getMovieService().findAllMovieByActorId(actorId);
        actor.ifPresent(a -> a.setMovies(allMovieByActorId));

        return actor.map(ActorMapper::toActorDtoWithMovies);
    }

    public List<Actor> findAllActorByMovieId(Integer movieId) {
        EntityValidator.validateId(movieId, "movie");
        return Optional.ofNullable(actorDao.findAllActorByMovieId(movieId)).orElse(Collections.emptyList());
    }

    public List<ActorDto> findAllActor() {
        return Optional.of(actorDao.findAll().stream().map(ActorMapper::toActorDto).toList()).orElse(Collections.emptyList());
    }

    public List<ActorDto> findActorsByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return actorDao.findActorsByPrefix(param).stream().map(ActorMapper::toActorDto).toList();
    }

    public void save(CreateOrUpdateActorDto createActorDto) {
        var valid = actorValidator.isValid(createActorDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        if (createActorDto.getId() == null || createActorDto.getId().isBlank()) {
            actorDao.save(ActorMapper.toActor(createActorDto));
        } else {
            actorDao.update(ActorMapper.toActor(createActorDto));
        }
    }

    public void delete(Integer actorId) {
        actorDao.delete(actorId);
    }
}
