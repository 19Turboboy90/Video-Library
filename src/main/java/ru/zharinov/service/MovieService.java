package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.MovieAllInfoDto;
import ru.zharinov.dto.MovieInfoDto;
import ru.zharinov.mapper.MovieAllInfoMapper;
import ru.zharinov.mapper.MovieMapper;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieService {
    private static final MovieService INSTANCE = new MovieService();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final MovieMapper movieMapper = MovieMapper.getInstance();
    private final MovieAllInfoMapper movieAllInfoMapper = MovieAllInfoMapper.getInstance();


    public List<MovieInfoDto> findAllMovies() {
        return movieDao.findAll().stream().map(movieMapper::mapper).toList();
    }

    public Optional<MovieAllInfoDto> findMovieById(Integer id) {
        return movieDao.findById(id).map(movieAllInfoMapper::mapper);
    }

    public static MovieService getInstance() {
        return INSTANCE;
    }
}
