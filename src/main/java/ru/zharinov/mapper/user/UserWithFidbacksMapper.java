package ru.zharinov.mapper.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.entity.User;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.mapper.feedback.FeedbackMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserWithFidbacksMapper implements Mapper<User, UserDto> {
    private static final UserWithFidbacksMapper INSTANCE = new UserWithFidbacksMapper();
    private static final FeedbackMapper feedbackMapper = FeedbackMapper.getInstance();

    @Override
    public UserDto mapper(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .feedbacks(object.getFeedbacks().stream().map(feedbackMapper::mapper).toList())
                .build();
    }

    public static UserWithFidbacksMapper getInstance() {
        return INSTANCE;
    }
}
