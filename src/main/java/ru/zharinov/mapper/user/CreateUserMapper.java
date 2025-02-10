package ru.zharinov.mapper.user;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.entity.Role;
import ru.zharinov.entity.User;
import ru.zharinov.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapper(CreateUserDto object) {
        return User.builder()
                .id(object.getId() == null ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole() == null ? Role.USER : Role.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}