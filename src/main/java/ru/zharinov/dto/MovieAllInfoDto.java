package ru.zharinov.dto;


import lombok.Builder;
import lombok.Value;

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
