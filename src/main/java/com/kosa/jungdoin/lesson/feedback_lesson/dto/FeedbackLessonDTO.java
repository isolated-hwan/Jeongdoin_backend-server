package com.kosa.jungdoin.lesson.feedback_lesson.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String content;
    private Integer price;
}