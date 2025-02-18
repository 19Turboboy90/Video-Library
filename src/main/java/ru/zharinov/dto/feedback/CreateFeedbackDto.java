package ru.zharinov.dto.feedback;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFeedbackDto {
    String text;
    String assessment;
    String movieId;
    String userId;
}