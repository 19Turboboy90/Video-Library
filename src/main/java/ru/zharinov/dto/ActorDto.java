package ru.zharinov.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ActorDto {
    Integer id;
    String name;
    LocalDate dateOfBirth;
}
