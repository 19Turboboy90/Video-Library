package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorMapper implements Mapper<Actor, ActorDto> {
    private static final ActorMapper INSTANCE = new ActorMapper();

    @Override
    public ActorDto mapper(Actor object) {
        return ActorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .build();
    }

    public static ActorMapper getInstance() {
        return INSTANCE;
    }
}
