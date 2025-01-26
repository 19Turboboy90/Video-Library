package ru.zharinov.mapper.director;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.entity.Director;
import ru.zharinov.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorMapper implements Mapper<Director, DirectorDto> {
    private static final DirectorMapper INSTANCE = new DirectorMapper();

    @Override
    public DirectorDto mapper(Director object) {
        return DirectorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .build();
    }

    public static DirectorMapper getInstance() {
        return INSTANCE;
    }
}
