package ru.zharinov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateDirectorDto {
    String name;
    String dateOfBirthday;
}