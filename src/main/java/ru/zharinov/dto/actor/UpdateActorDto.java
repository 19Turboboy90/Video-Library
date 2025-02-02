package ru.zharinov.dto.actor;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateActorDto {
    String id;
    String name;
    String dateOfBirthday;
}
