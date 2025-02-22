package ru.zharinov.service;

import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.movie.CreateMovieDto;
import ru.zharinov.dto.movie.MovieAllInfoDto;
import ru.zharinov.dto.movie.MovieInfoDto;
import ru.zharinov.entity.Director;
import ru.zharinov.entity.Movie;
import ru.zharinov.mapper.movie.CreateOrUpdateMovieMapper;
import ru.zharinov.mapper.movie.MovieAllInfoMapper;
import ru.zharinov.mapper.movie.MovieMapper;
import ru.zharinov.validation.EntityValidator;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class MovieService {
    private final FactoryService factoryService;
    private final MovieDao movieDao = MovieDao.getInstance();
    private final MovieMapper movieMapper = MovieMapper.getInstance();
    private final MovieAllInfoMapper movieAllInfoMapper = MovieAllInfoMapper.getInstance();
    private final FeedbackService feedbackService = FeedbackService.getInstance();
    private final CreateOrUpdateMovieMapper createOrUpdateMovieMapper = CreateOrUpdateMovieMapper.getInstance();

    public MovieService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public List<MovieInfoDto> findAllMovies() {
        return Optional.of(movieDao.findAll().stream().map(movieMapper::mapper).toList())
                .orElse(emptyList());
    }

    public List<Movie> findAllMovieByActorId(Integer actorId) {
        EntityValidator.validateId(actorId, "actor");
        return Optional.of(movieDao.findAllMovieByActorId(actorId)).orElse(emptyList());
    }

    public List<Movie> findAllMoviesByDirectorId(Integer directorId) {
        EntityValidator.validateId(directorId, "director");
        return Optional.of(movieDao.findAllMoviesByDirectorId(directorId)).orElse(emptyList());
    }

    public List<MovieInfoDto> findAllMoviesByDate(Integer fromDate, Integer toFrom) {
        return Optional.of(movieDao.findAllMoviesByDate(fromDate, toFrom).stream().map(movieMapper::mapper).toList())
                .orElse(emptyList());
    }

    public List<MovieInfoDto> findMoviesByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return movieDao.findMoviesByPrefix(param).stream().map(movieMapper::mapper).toList();
    }

    public Optional<Movie> findById(Integer movieId) {
        EntityValidator.validateId(movieId, "movieId");
        return movieDao.findById(movieId);
    }

    public Optional<MovieAllInfoDto> findMovieById(Integer movieId) {
        EntityValidator.validateId(movieId, "movie");
        var directorByMovieId = factoryService.getDirectorService().findDirectorByMovieId(movieId);
        //Поиск актеров по id фильма
        var allActorByMovieId = factoryService.getActorService().findAllActorByMovieId(movieId);
        var allFeedbackByMovieId = feedbackService.findAllFeedbackByMovieId(movieId);
        var movie = movieDao.findById(movieId);
        EntityValidator.validateEntityExists(movie, movieId, "movie");
        movie.ifPresent(mov -> mov.setActors(allActorByMovieId));
        movie.ifPresent(mov -> mov.setDirector(directorByMovieId.orElseGet(Director::new)));
        movie.ifPresent(mov -> mov.setFeedbacks(allFeedbackByMovieId));

        return movie.map(movieAllInfoMapper::mapper);
    }

    public void saveOrUpdateMovie(CreateMovieDto createMovieDto) throws SQLException {
        var directorById =
                factoryService.getDirectorService().findById(Integer.parseInt(createMovieDto.getDirectorId()));
        EntityValidator.validateEntityExists(directorById, createMovieDto.getDirectorId(), "directorId");
        var movie = createOrUpdateMovieMapper.mapper(createMovieDto);
        movie.setDirector(directorById.orElseThrow());
        var actorsIdList = Arrays.stream(createMovieDto.getActorsId()).map(Integer::parseInt).toList();
        if (createMovieDto.getId() == null || createMovieDto.getId().isBlank()) {
            movieDao.save(movie);
            actorsIdList.forEach(actorId -> movieDao.saveMovieIdAndActorIdToActorMovie(actorId, movie.getId()));
        } else {
            movieDao.deleteMoviesFromActorMovie(Integer.parseInt(createMovieDto.getId()));
            movieDao.update(movie);
            actorsIdList.forEach(actorId -> movieDao.saveMovieIdAndActorIdToActorMovie(actorId, movie.getId()));
        }
    }
}
