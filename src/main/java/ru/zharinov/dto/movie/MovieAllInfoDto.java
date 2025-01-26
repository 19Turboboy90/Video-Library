package ru.zharinov.dto.movie;


import lombok.Builder;
import lombok.Value;
import ru.zharinov.dto.actor.ActorDto;
import ru.zharinov.dto.director.DirectorDto;
import ru.zharinov.dto.director.DirectorWithMoviesDto;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class MovieAllInfoDto {
    Integer id;
    String name;
    LocalDate premierDate;
    String country;
    String genre;
    DirectorDto director;
    List<ActorDto> actors;
}
