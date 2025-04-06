package ru.zharinov.mapper;

import lombok.experimental.UtilityClass;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.entity.Director;
import ru.zharinov.util.DateFormatter;

@UtilityClass
public class DirectorMapper {
    public static DirectorDto toDirectorDto(Director object) {
        return DirectorDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .build();
    }

    public static Director toDirector(CreateDirectorDto object) {
        return Director.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .dateOfBirthday(DateFormatter.format(object.getDateOfBirthday()))
                .build();
    }

    public static DirectorWithMoviesDto toDirectorWithMoviesDto(Director object) {
        return DirectorWithMoviesDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .movies(object.getMovies().stream().map(MovieMapper::toMovieInfoDto).toList())
                .build();
    }
}
