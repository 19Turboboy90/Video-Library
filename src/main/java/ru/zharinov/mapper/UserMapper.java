package ru.zharinov.mapper;

import lombok.experimental.UtilityClass;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.entity.Role;
import ru.zharinov.entity.User;

@UtilityClass
public class UserMapper {
    public static UserDto toUserDto(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .build();
    }

    public static UserDto toUserDtoWithFeedbacks(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .feedbacks(object.getFeedbacks().stream().map(FeedbackMapper::toFeedbackDto).toList())
                .build();
    }

    public static User toUser(CreateUserDto object) {
        return User.builder()
                .id(object.getId() == null ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole() == null ? Role.USER : Role.valueOf(object.getRole()))
                .build();
    }
}
