package ru.zharinov.mapper.feedback;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zharinov.dto.feedback.FeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.mapper.Mapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMapper implements Mapper<Feedback, FeedbackDto> {
    private static final FeedbackMapper INSTANCE = new FeedbackMapper();


    @Override
    public FeedbackDto mapper(Feedback object) {
        return FeedbackDto.builder()
                .id(object.getId())
                .text(object.getText())
                .assessment(object.getAssessment())
                .movie(object.getMovie())
                .user(object.getUser())
                .build();
    }

    public static FeedbackMapper getInstance() {
        return INSTANCE;
    }
}
