package ru.zharinov.dto.feedback;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class CreateFeedbackDto {
    String text;
    String assessment;
    String movieId;
    String userId;
}