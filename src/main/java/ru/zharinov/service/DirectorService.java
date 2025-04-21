package ru.zharinov.service;

import lombok.Setter;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.entity.Director;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.DirectorMapper;
import ru.zharinov.validation.DirectorValidation;
import ru.zharinov.validation.EntityValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DirectorService {
    private final DirectorDao directorDao;
    @Setter
    private MovieService movieService;
    private final DirectorValidation validation = DirectorValidation.getInstance();

    public DirectorService(DirectorDao directorDao) {
        this.directorDao = directorDao;
    }

    public Optional<DirectorWithMoviesDto> findDirectorById(Integer directorId) {
        EntityValidator.validateId(directorId, "director");
        var directorByMovieId = directorDao.findById(directorId);
        EntityValidator.validateEntityExists(directorByMovieId, directorId, "director");
        var allMoviesByDirectorId = movieService.findAllMoviesByDirectorId(directorId);
        directorByMovieId.ifPresent(d -> d.setMovies(allMoviesByDirectorId));

        return directorByMovieId.map(DirectorMapper::toDirectorWithMoviesDto);
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
            directorDao.save(DirectorMapper.toDirector(directorDto));
        } else {
            directorDao.update(DirectorMapper.toDirector(directorDto));
        }
    }

    public List<DirectorDto> findAllDirectors() {
        return Optional.of(directorDao.findAll().stream().map(DirectorMapper::toDirectorDto).toList())
                .orElse(Collections.emptyList());
    }

    public List<DirectorDto> findDirectorsByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return directorDao.finDirectorsByPrefix(param).stream().map(DirectorMapper::toDirectorDto).toList();
    }

    public boolean delete(Integer directorId) {
        return directorDao.delete(directorId);
    }

    public Optional<Director> findById(Integer directorId) {
        return directorDao.findById(directorId);
    }
}
