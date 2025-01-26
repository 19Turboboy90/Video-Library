package ru.zharinov.dto.director;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateDirectorDto {
    String name;
    String dateOfBirthday;
}