package ru.zharinov.mapper.actor;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.entity.Actor;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateActorMapper implements Mapper<CreateOrUpdateActorDto, Actor> {
    private static final CreateActorMapper INSTANCE = new CreateActorMapper();

    @Override
    public Actor mapper(CreateOrUpdateActorDto object) {
        return Actor.builder()
                .id(Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static CreateActorMapper getInstance() {
        return INSTANCE;
    }
}
