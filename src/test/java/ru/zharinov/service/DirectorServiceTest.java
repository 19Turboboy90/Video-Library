package ru.zharinov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zharinov.dao.DirectorDao;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.entity.Director;
import ru.zharinov.entity.Movie;
import ru.zharinov.mapper.DirectorMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class DirectorServiceTest {
    private static final Director DIRECTOR =
            new Director(1, "testDirector", LocalDate.of(1980, 1, 1), List.of());
    private static final Movie MOVIE = new Movie(1, "TestMovie", LocalDate.now(),
            "testCountry", "testGender", DIRECTOR, List.of(), null);

    @Mock
    private DirectorDao directorDao;
    @Mock
    private MovieService movieService;

    @InjectMocks
    private DirectorService directorService;

    @BeforeEach
    void setUp() {
        directorService.setMovieService(movieService);
    }

    @Test
    void testServiceInjection() {
        assertThat(movieService).isNotNull();
    }

    @Test
    void findDirectorById() {
        when(directorDao.findById(DIRECTOR.getId())).thenReturn(Optional.of(DIRECTOR));

        var directorById = directorService.findDirectorById(DIRECTOR.getId());

        assertThat(directorById).isEqualTo(Optional.of(DirectorMapper.toDirectorWithMoviesDto(DIRECTOR)));
    }

    @Test
    void findDirectorByMovieId() {
        when(directorDao.findDirectorByMovieId(MOVIE.getId())).thenReturn(Optional.of(DIRECTOR));
        var directorByMovieId = directorService.findDirectorByMovieId(MOVIE.getId());

        assertThat(directorByMovieId).isEqualTo(Optional.of(DIRECTOR));
    }

    @Test
    void update() {
        doNothing().when(directorDao).update(any(Director.class));
        directorService.save(getCreateDirectorDto("1"));

        verify(directorDao).update(any(Director.class));
    }

    @Test
    void findAllDirectors() {
        when(directorDao.findAll()).thenReturn(List.of(DIRECTOR));

        var allDirectors = directorService.findAllDirectors();

        assertThat(allDirectors).isEqualTo(List.of(DirectorMapper.toDirectorDto(DIRECTOR)));
    }

    @Test
    void findDirectorsByPrefix() {
        when(directorDao.finDirectorsByPrefix("test")).thenReturn(List.of(DIRECTOR));

        var findDirectors = directorService.findDirectorsByPrefix("test");

        assertThat(findDirectors).isEqualTo(List.of(DirectorMapper.toDirectorDto(DIRECTOR)));
    }

    @Test
    void delete() {
        when(directorDao.delete(DIRECTOR.getId())).thenReturn(true);

        var delete = directorService.delete(DIRECTOR.getId());
        assertThat(delete).isTrue();
        verify(directorDao).delete(DIRECTOR.getId());
    }

    @Test
    void deleteIsNotExistId() {
        when(directorDao.delete(-1)).thenReturn(false);

        var delete = directorService.delete(-1);
        assertThat(delete).isFalse();
        verify(directorDao).delete(-1);
    }

    private CreateDirectorDto getCreateDirectorDto(String id) {
        return CreateDirectorDto.builder()
                .id(id.isEmpty() ? null : id)
                .name(DIRECTOR.getName())
                .dateOfBirthday(DIRECTOR.getDateOfBirthday().toString())
                .build();
    }
}