package com.kosa.jungdoin.lesson.online_lesson.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnlineLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String title;
    private String content;
    private Integer price;
}