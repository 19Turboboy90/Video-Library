package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.CreateDirectorDto;
import ru.zharinov.entity.Director;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateDirectorMapper implements Mapper<CreateDirectorDto, Director> {
    public static final CreateDirectorMapper INSTANCE = new CreateDirectorMapper();


    @Override
    public Director mapper(CreateDirectorDto object) {
        return Director.builder()
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static CreateDirectorMapper getInstance() {
        return INSTANCE;
    }
}