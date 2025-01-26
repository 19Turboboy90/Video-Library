package ru.zharinov.dto.director;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class DirectorDto {
    Integer id;
    String name;
    LocalDate dateOfBirthday;
}
