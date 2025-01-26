package ru.zharinov.dto.movie;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MovieInfoDto {
    Integer id;
    String description;
}
