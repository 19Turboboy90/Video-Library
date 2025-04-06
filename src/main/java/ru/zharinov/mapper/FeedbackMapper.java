package ru.zharinov.mapper;

import lombok.experimental.UtilityClass;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.dto.feedback.FeedbackDto;
import ru.zharinov.entity.Feedback;

@UtilityClass
public class FeedbackMapper {
    public static FeedbackDto toFeedbackDto(Feedback object) {
        return FeedbackDto.builder()
                .id(object.getId())
                .text(object.getText())
                .assessment(object.getAssessment())
                .movie(object.getMovie())
                .user(object.getUser())
                .build();
    }

    public static Feedback toFeedback(CreateFeedbackDto createFeedbackDto) {
        return Feedback.builder()
                .text(createFeedbackDto.getText())
                .assessment(Integer.parseInt(createFeedbackDto.getAssessment()))
                .build();
    }
}
