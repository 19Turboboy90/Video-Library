package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.MovieAllInfoDto;
import ru.zharinov.dto.MovieInfoDto;
import ru.zharinov.entity.Director;
import ru.zharinov.mapper.MovieAllInfoMapper;
import ru.zharinov.mapper.MovieMapper;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieService {
    private static final MovieService INSTANCE = new MovieService();
    private final MovieDao movieDao = MovieDao.getInstance();
    private final DirectorDao directorDao = DirectorDao.getInstance();
    private final ActorDao actorDao = ActorDao.getInstance();
    private final MovieMapper movieMapper = MovieMapper.getInstance();
    private final MovieAllInfoMapper movieAllInfoMapper = MovieAllInfoMapper.getInstance();


//    public List<MovieInfoDto> findAllMovies() {
//        return movieDao.findAll().stream().map(movieMapper::mapper).toList();
//    }

    public List<MovieInfoDto> findAllMoviesByDate(Integer fromDate, Integer toFrom) {
        return movieDao.findAllMoviesByDate(fromDate, toFrom).stream().map(movieMapper::mapper).toList();
    }

    public Optional<MovieAllInfoDto> findMovieById(Integer movieId) {
        //Поиск режиссера по id фильма
        var directorByMovieId = directorDao.findDirectorByMovieId(movieId);
        //Поиск актеров по id фильма
        var allActorByMovieId = actorDao.findAllActorByMovieId(movieId);
        var movie = movieDao.findById(movieId);
        if (movie.isPresent()) {
            movie.get().setActors(allActorByMovieId);
            movie.get().setDirector(directorByMovieId.orElseGet(Director::new));
        }

        return movie.map(movieAllInfoMapper::mapper);
    }

    public static MovieService getInstance() {
        return INSTANCE;
    }
}
