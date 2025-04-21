package ru.zharinov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zharinov.dao.ActorDao;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.entity.Movie;
import ru.zharinov.mapper.ActorMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ActorServiceTest {
    private final static Actor ACTOR =
            new Actor(1, "testActor", LocalDate.of(2000, 1, 1), null);
    private static final Movie MOVIE = new Movie(1, "TestMovie", LocalDate.now(),
            "testCountry", "testGender", null, List.of(ACTOR), null);

    @Mock
    private ActorDao actorDao;
    @Mock
    private MovieService movieService;

    @InjectMocks
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        actorService.setMovieService(movieService);
    }

    @Test
    void testServiceInjection() {
        assertThat(movieService).isNotNull();
    }

    @Test
    void findActorById() {
        when(actorDao.findById(ACTOR.getId())).thenReturn(Optional.of(ACTOR));

        var actorById = actorService.findActorById(ACTOR.getId());

        assertThat(actorById).isEqualTo(Optional.of(ActorMapper.toActorDtoWithMovies(ACTOR)));
    }

    @Test
    void findAllActorByMovieId() {
        when(actorDao.findAllActorByMovieId(MOVIE.getId())).thenReturn(List.of(ACTOR));

        var listActors = actorService.findAllActorByMovieId(MOVIE.getId());

        assertThat(listActors).isEqualTo(List.of(ACTOR));
    }

    @Test
    void findAllActor() {
        when(actorDao.findAll()).thenReturn(List.of(ACTOR));

        var allActor = actorService.findAllActor();

        assertThat(allActor).isEqualTo(List.of(ActorMapper.toActorDto(ACTOR)));
    }

    @Test
    void findActorsByPrefix() {
        when(actorDao.findActorsByPrefix("test")).thenReturn(List.of(ACTOR));

        var findActors = actorService.findActorsByPrefix("test");

        assertThat(findActors).isEqualTo(List.of(ActorMapper.toActorDto(ACTOR)));
    }

    @Test
    void update() {
        doNothing().when(actorDao).update(any(Actor.class));
        actorService.save(getCreateActorDto("1"));

        verify(actorDao).update(any(Actor.class));
    }

    @Test
    void delete() {
        when(actorDao.delete(ACTOR.getId())).thenReturn(true);

        var delete = actorService.delete(ACTOR.getId());
        assertThat(delete).isTrue();
        verify(actorDao).delete(ACTOR.getId());
    }

    @Test
    void deleteIsNotExistId() {
        when(actorDao.delete(-1)).thenReturn(false);

        var delete = actorService.delete(-1);
        assertThat(delete).isFalse();
        verify(actorDao).delete(-1);
    }

    private CreateOrUpdateActorDto getCreateActorDto(String id) {
        return CreateOrUpdateActorDto.builder()
                .id(id.isEmpty() ? null : id)
                .name(ACTOR.getName())
                .dateOfBirthday(ACTOR.getDateOfBirthday().toString())
                .build();
    }
}