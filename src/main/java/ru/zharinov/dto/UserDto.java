package ru.zharinov.dto;

import lombok.Builder;
import lombok.Value;
import ru.zharinov.entity.Role;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    String email;
    String password;
    Role role;
}
