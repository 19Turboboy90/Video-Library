package ru.zharinov.mapper;

import lombok.experimental.UtilityClass;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.util.DateFormatter;

@UtilityClass
public class ActorMapper {

    public static ActorDto toActorDto(Actor object) {
        return ActorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .build();
    }

    public static ActorDto toActorDtoWithMovies(Actor object) {
        return ActorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .movies(object.getMovies().stream().map(MovieMapper::toMovieInfoDto).toList())
                .build();
    }

    public static Actor toActor(CreateOrUpdateActorDto object) {
        return Actor.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }
}
