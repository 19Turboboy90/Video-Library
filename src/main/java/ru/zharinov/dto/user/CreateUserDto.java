package ru.zharinov.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String id;
    String name;
    String email;
    String password;
    String role;
}