package ru.zharinov.dto.movie;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateMovieDto {
    String id;
    String name;
    String premiereDate;
    String country;
    String genre;
    String directorId;
    String[] actorsId;
}
