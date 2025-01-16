package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.DirectorDto;
import ru.zharinov.mapper.DirectorWithMoviesMapper;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorService {
    private static final DirectorService INSTANCE = new DirectorService();
    private final DirectorDao directorDao = DirectorDao.getInstance();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final DirectorWithMoviesMapper directorMapper = DirectorWithMoviesMapper.getInstance();

    public Optional<DirectorDto> findDirectorById(Integer directorId) {
        var directorByMovieId = directorDao.findDirectorByMovieId(directorId);
        if (directorByMovieId.isPresent()) {
            var allMoviesByDirectorId = movieDao.findAllMoviesByDirectorId(directorId);
            directorByMovieId.get().setMovies(allMoviesByDirectorId);
        } else {
            throw new RuntimeException("ID is not found = " + directorId);
        }
        return directorByMovieId.map(directorMapper::mapper);
    }

    public static DirectorService getInstance() {
        return INSTANCE;
    }
}
