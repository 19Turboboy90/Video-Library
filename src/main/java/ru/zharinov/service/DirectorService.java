package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.director.CreateDirectorMapper;
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
    private final CreateDirectorMapper createDirectorMapper = CreateDirectorMapper.getInstance();
    private final DirectorMapper directorMapper = DirectorMapper.getInstance();
    private final DirectorValidation validation = DirectorValidation.getInstance();

    public Optional<DirectorWithMoviesDto> findDirectorById(Integer directorId) {
        var directorByMovieId = directorDao.findDirectorByMovieId(directorId);
        if (directorByMovieId.isPresent()) {
            var allMoviesByDirectorId = movieDao.findAllMoviesByDirectorId(directorId);
            directorByMovieId.get().setMovies(allMoviesByDirectorId);
        } else {
            throw new RuntimeException("ID is not found = " + directorId);
        }
        return directorByMovieId.map(directorWithMoviesMapper::mapper);
    }

    public Integer save(CreateDirectorDto directorDto) {
        var valid = validation.isValid(directorDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        var director = createDirectorMapper.mapper(directorDto);
        directorDao.save(director);
        return director.getId();
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
}
