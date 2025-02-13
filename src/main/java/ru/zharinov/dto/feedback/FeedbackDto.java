package ru.zharinov.dto.feedback;

import lombok.Builder;
import lombok.Value;
import ru.zharinov.entity.Movie;
import ru.zharinov.entity.User;

@Value
@Builder
public class FeedbackDto {
    Integer id;
    String text;
    Integer assessment;
    Movie movie;
    User user;
}
