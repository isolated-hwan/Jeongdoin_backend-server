package com.kosa.jungdoin.lesson.personal_lesson.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PersonalLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String title;
    private Integer price;
    private String content;
    private String location;
    private BigDecimal lat;
    private BigDecimal lng;
}
