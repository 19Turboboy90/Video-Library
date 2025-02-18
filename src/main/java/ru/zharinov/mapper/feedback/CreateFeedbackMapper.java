package ru.zharinov.mapper.feedback;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateFeedbackMapper implements Mapper<CreateFeedbackDto, Feedback> {
    private static final CreateFeedbackMapper INSTANCE = new CreateFeedbackMapper();

    @Override
    public Feedback mapper(CreateFeedbackDto createFeedbackDto) {
        return Feedback.builder()
                .text(createFeedbackDto.getText())
                .assessment(Integer.parseInt(createFeedbackDto.getAssessment()))
                .build();
    }

    public static CreateFeedbackMapper getInstance() {
        return INSTANCE;
    }
}