package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.ActorDto;
import ru.zharinov.entity.Actor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActorMapper implements Mapper<Actor, ActorDto> {
    private static final ActorMapper INSTANCE = new ActorMapper();

    @Override
    public ActorDto mapper(Actor object) {
        return ActorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirth(object.getDateOfBirthday())
                .build();
    }

    public static ActorMapper getInstance() {
        return INSTANCE;
    }
}
