package ru.zharinov.service;

import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.entity.Director;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.director.CreateOrUpdateDirectorMapper;
import ru.zharinov.mapper.director.DirectorMapper;
import ru.zharinov.mapper.director.DirectorWithMoviesMapper;
import ru.zharinov.validation.DirectorValidation;
import ru.zharinov.validation.EntityValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DirectorService {
    private final FactoryService factoryService;
    private final DirectorDao directorDao = DirectorDao.getInstance();
    private final DirectorWithMoviesMapper directorWithMoviesMapper = DirectorWithMoviesMapper.getInstance();
    private final CreateOrUpdateDirectorMapper createOrUpdateDirectorMapper = CreateOrUpdateDirectorMapper.getInstance();
    private final DirectorMapper directorMapper = DirectorMapper.getInstance();
    private final DirectorValidation validation = DirectorValidation.getInstance();

    public DirectorService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public Optional<DirectorWithMoviesDto> findDirectorById(Integer directorId) {
        EntityValidator.validateId(directorId, "director");
        var directorByMovieId = directorDao.findById(directorId);
        EntityValidator.validateEntityExists(directorByMovieId, directorId, "director");
        var allMoviesByDirectorId = factoryService.getMovieService().findAllMoviesByDirectorId(directorId);
        directorByMovieId.ifPresent(d -> d.setMovies(allMoviesByDirectorId));

        return directorByMovieId.map(directorWithMoviesMapper::mapper);
    }

    public Optional<Director> findDirectorByMovieId(Integer movieId) {
        EntityValidator.validateId(movieId, "movie");
        var directorByMovieId = directorDao.findDirectorByMovieId(movieId);
        EntityValidator.validateEntityExists(directorByMovieId, movieId, "director");
        return directorByMovieId;
    }

    public void save(CreateDirectorDto directorDto) {
        var valid = validation.isValid(directorDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        if (directorDto.getId() == null || directorDto.getId().isBlank()) {
            directorDao.save(createOrUpdateDirectorMapper.mapper(directorDto));
        } else {
            directorDao.update(createOrUpdateDirectorMapper.mapper(directorDto));
        }
    }

    public List<DirectorDto> findAllDirectors() {
        return Optional.of(directorDao.findAll().stream().map(directorMapper::mapper).toList())
                .orElse(Collections.emptyList());
    }

    public List<DirectorDto> findDirectorsByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return directorDao.finDirectorsByPrefix(param).stream().map(directorMapper::mapper).toList();
    }

    public void delete(Integer directorId) {
        directorDao.delete(directorId);
    }

    public Optional<Director> findById(Integer directorId) {
        return directorDao.findById(directorId);
    }
}
