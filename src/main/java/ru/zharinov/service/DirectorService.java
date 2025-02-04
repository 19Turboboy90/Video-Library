package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.director.CreateOrUpdateDirectorMapper;
import ru.zharinov.mapper.director.DirectorMapper;
import ru.zharinov.mapper.director.DirectorWithMoviesMapper;
import ru.zharinov.validation.DirectorValidation;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorService {
    private static final DirectorService INSTANCE = new DirectorService();
    private final DirectorDao directorDao = DirectorDao.getInstance();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final DirectorWithMoviesMapper directorWithMoviesMapper = DirectorWithMoviesMapper.getInstance();
    private final CreateOrUpdateDirectorMapper createOrUpdateDirectorMapper = CreateOrUpdateDirectorMapper.getInstance();
    private final DirectorMapper directorMapper = DirectorMapper.getInstance();
    private final DirectorValidation validation = DirectorValidation.getInstance();

    public Optional<DirectorWithMoviesDto> findDirectorById(Integer directorId) {
        var directorByMovieId = directorDao.findById(directorId);
        if (directorByMovieId.isPresent()) {
            var allMoviesByDirectorId = movieDao.findAllMoviesByDirectorId(directorId);
            directorByMovieId.get().setMovies(allMoviesByDirectorId);
        } else {
            throw new RuntimeException("The movie wasn't found by directorId = " + directorId);
        }
        return directorByMovieId.map(directorWithMoviesMapper::mapper);
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

    public static DirectorService getInstance() {
        return INSTANCE;
    }

    public List<DirectorDto> findAllDirectors() {
        return directorDao.findAll().stream().map(directorMapper::mapper).toList();
    }

    public List<DirectorDto> findDirectorsByPrefix(String prefix) {
        return directorDao.finDirectorsByPrefix(prefix).stream().map(directorMapper::mapper).toList();
    }

    public void delete(Integer directorId) {
        directorDao.delete(directorId);
    }
}
