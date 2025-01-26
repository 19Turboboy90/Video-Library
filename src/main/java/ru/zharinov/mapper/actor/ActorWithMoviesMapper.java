package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.mapper.movie.MovieMapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorWithMoviesMapper implements Mapper<Actor, ActorDto> {
    private static final ActorWithMoviesMapper INSTANCE = new ActorWithMoviesMapper();
    private final MovieMapper movieMapper = MovieMapper.getInstance();

    @Override
    public ActorDto mapper(Actor object) {
        return ActorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .movies(object.getMovies().stream().map(movieMapper::mapper).toList())
                .build();
    }

    public static ActorWithMoviesMapper getInstance() {
        return INSTANCE;
    }
}
