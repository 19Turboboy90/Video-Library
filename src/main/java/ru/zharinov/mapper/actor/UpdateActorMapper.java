package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.UpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UpdateActorMapper implements Mapper<UpdateActorDto, Actor> {
    private static final UpdateActorMapper INSTANCE = new UpdateActorMapper();

    @Override
    public Actor mapper(UpdateActorDto object) {
        return Actor.builder()
                .id(Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static UpdateActorMapper getInstance() {
        return INSTANCE;
    }
}
