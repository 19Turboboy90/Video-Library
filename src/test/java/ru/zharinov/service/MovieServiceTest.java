package ru.zharinov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zharinov.dao.MovieDao;
import ru.zharinov.dto.movie.CreateMovieDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.entity.Director;
import ru.zharinov.entity.Movie;
import ru.zharinov.mapper.MovieMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    private static final Director DIRECTOR =
            new Director(1, "testDirector", LocalDate.of(1980, 1, 1), List.of());
    private final static Actor ACTOR =
            new Actor(1, "testActor", LocalDate.of(2000, 1, 1), null);
    private static final Movie MOVIE_1 = new Movie(1, "testMovie", LocalDate.of(1990, 1, 1),
            "testCountry", "testGender", DIRECTOR, List.of(ACTOR), null);
    private static final Movie MOVIE_2 = new Movie(1, "testMovie", LocalDate.of(1991, 1, 1),
            "testCountry", "testGender", DIRECTOR, List.of(), null);

    @Mock
    private MovieDao movieDao;
    @Mock
    private DirectorService directorService;
    @Mock
    private ActorService actorService;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieService.setDirectorService(directorService);
        movieService.setActorService(actorService);
    }

    @Test
    void testServiceInjection() {
        assertThat(directorService).isNotNull();
        assertThat(actorService).isNotNull();
    }

    @Test
    void findAllMovies() {
        when(movieDao.findAll()).thenReturn(List.of(MOVIE_1, MOVIE_2));

        var allMovies = movieService.findAllMovies();

        assertThat(allMovies).isEqualTo(List.of(MovieMapper.toMovieInfoDto(MOVIE_1), MovieMapper.toMovieInfoDto(MOVIE_2)));
    }

    @Test
    void findAllMovieByActorId() {
        when(movieDao.findAllMovieByActorId(ACTOR.getId())).thenReturn(List.of(MOVIE_1));
        var allMoviesByActorId = movieService.findAllMovieByActorId(ACTOR.getId());

        assertThat(allMoviesByActorId).isEqualTo(List.of(MOVIE_1));
    }

    @Test
    void findAllMoviesByDirectorId() {
        when(movieDao.findAllMoviesByDirectorId(DIRECTOR.getId())).thenReturn(List.of(MOVIE_1, MOVIE_2));
        var allMoviesByDirectorId = movieService.findAllMoviesByDirectorId(DIRECTOR.getId());

        assertThat(allMoviesByDirectorId).isEqualTo(List.of(MOVIE_1, MOVIE_2));
    }

    @Test
    void findAllMoviesByDate() {
        when(movieDao.findAllMoviesByDate(1990, 1992)).thenReturn(List.of(MOVIE_1, MOVIE_2));

        var allMoviesByDate = movieService.findAllMoviesByDate(1990, 1992);

        assertThat(allMoviesByDate)
                .isEqualTo(List.of(MovieMapper.toMovieInfoDto(MOVIE_1), MovieMapper.toMovieInfoDto(MOVIE_2)));
    }

    @Test
    void findAllMoviesByDateWhenDateIsNotCorrect() {
        when(movieDao.findAllMoviesByDate(1993, 1996)).thenReturn(List.of());

        var allMoviesByDate = movieService.findAllMoviesByDate(1993, 1996);

        assertThat(allMoviesByDate).isEqualTo(List.of());
    }

    @Test
    void findMoviesByPrefix() {
        when(movieDao.findMoviesByPrefix("test")).thenReturn(List.of(MOVIE_1));

        var allMovies = movieService.findMoviesByPrefix("test");

        assertThat(allMovies).isEqualTo(List.of(MovieMapper.toMovieInfoDto(MOVIE_1)));
    }

    @Test
    void findMoviesByPrefixWhenPrefixIsNotCorrect() {
        when(movieDao.findMoviesByPrefix("-1")).thenReturn(List.of());

        var allMovies = movieService.findMoviesByPrefix("-1");

        assertThat(allMovies).isEqualTo(List.of());
    }

    @Test
    void findMovieById() {
        when(movieDao.findById(MOVIE_1.getId())).thenReturn(Optional.of(MOVIE_1));

        var movieById = movieService.findMovieById(MOVIE_1.getId());

        assertThat(movieById).isEqualTo(Optional.of(MovieMapper.toMovieAllInfoDto(MOVIE_1)));
    }

    @Test
    void update() {
        when(directorService.findById(DIRECTOR.getId())).thenReturn(Optional.of(DIRECTOR));
        doNothing().when(movieDao).update(any(Movie.class));
        try {
            movieService.saveOrUpdateMovie(getCreateMovieDto("1"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        verify(movieDao).update(any(Movie.class));
    }

    @Test
    void delete() {
        when(movieDao.delete(MOVIE_1.getId())).thenReturn(true);

        var delete = movieService.deleteMovieById(MOVIE_1.getId());
        assertThat(delete).isTrue();
        verify(movieDao).delete(MOVIE_1.getId());
    }

    @Test
    void deleteIsNotExistId() {
        when(movieDao.delete(-1)).thenReturn(false);

        var delete = movieService.deleteMovieById(-1);
        assertThat(delete).isFalse();
        verify(movieDao).delete(-1);
    }

    private CreateMovieDto getCreateMovieDto(String id) {
        return CreateMovieDto.builder()
                .id(id)
                .name(MOVIE_1.getName())
                .premiereDate(String.valueOf(MOVIE_1.getPremierDate()))
                .country(MOVIE_1.getCountry())
                .genre(MOVIE_1.getGenre())
                .directorId(String.valueOf(DIRECTOR.getId()))
                .actorsId(new String[]{String.valueOf(ACTOR.getId())})
                .build();
    }
}