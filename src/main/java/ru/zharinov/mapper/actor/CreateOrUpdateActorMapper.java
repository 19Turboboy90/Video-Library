package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrUpdateActorMapper implements Mapper<CreateOrUpdateActorDto, Actor> {
    private static final CreateOrUpdateActorMapper INSTANCE = new CreateOrUpdateActorMapper();

    @Override
    public Actor mapper(CreateOrUpdateActorDto object) {
        return Actor.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static CreateOrUpdateActorMapper getInstance() {
        return INSTANCE;
    }
}
