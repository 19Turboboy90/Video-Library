package ru.zharinov.dto.actor;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateActorDto {
    String name;
    String dateOfBirthday;
}
