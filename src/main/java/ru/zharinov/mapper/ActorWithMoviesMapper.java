package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.ActorDto;
import ru.zharinov.entity.Actor;

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
