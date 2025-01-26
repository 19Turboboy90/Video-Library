package ru.zharinov.dto.actor;

import lombok.Builder;
import lombok.Value;
import ru.zharinov.dto.movie.MovieInfoDto;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ActorDto {
    Integer id;
    String name;
    LocalDate dateOfBirthday;
    List<MovieInfoDto> movies;
}
