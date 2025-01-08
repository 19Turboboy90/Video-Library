package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.MovieInfoDto;
import ru.zharinov.mapper.MovieMapper;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieService {
    private static final MovieService INSTANCE = new MovieService();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final ActorDao actorDao = ActorDao.getInstance();
    private final MovieMapper movieMapper = MovieMapper.getInstance();

    public List<MovieInfoDto> findAllMovies() {
        return movieDao.findAll().stream().map(movieMapper::mapper).toList();
    }

    public static MovieService getInstance() {
        return INSTANCE;
    }
}
