package ru.zharinov.mapper.director;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.entity.Director;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrUpdateDirectorMapper implements Mapper<CreateDirectorDto, Director> {
    private static final CreateOrUpdateDirectorMapper INSTANCE = new CreateOrUpdateDirectorMapper();

    @Override
    public Director mapper(CreateDirectorDto object) {
        return Director.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static CreateOrUpdateDirectorMapper getInstance() {
        return INSTANCE;
    }
}