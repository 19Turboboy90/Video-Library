package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.CreateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateActorMapper implements Mapper<CreateActorDto, Actor> {
    private static final CreateActorMapper INSTANCE = new CreateActorMapper();

    @Override
    public Actor mapper(CreateActorDto object) {
        return Actor.builder()
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static CreateActorMapper getInstance() {
        return INSTANCE;
    }
}
