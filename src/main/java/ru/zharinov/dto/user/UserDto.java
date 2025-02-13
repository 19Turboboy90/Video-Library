package ru.zharinov.dto.user;

import lombok.Builder;
import lombok.Value;
import ru.zharinov.dto.feedback.FeedbackDto;
import ru.zharinov.entity.Role;

import java.util.List;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    String email;
    String password;
    Role role;
    List<FeedbackDto> feedbacks;
}
