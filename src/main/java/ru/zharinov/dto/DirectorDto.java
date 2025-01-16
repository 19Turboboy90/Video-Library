package ru.zharinov.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class DirectorDto {
    Integer id;
    String name;
    LocalDate dateOfBirthday;
    List<MovieInfoDto> movies;
}
