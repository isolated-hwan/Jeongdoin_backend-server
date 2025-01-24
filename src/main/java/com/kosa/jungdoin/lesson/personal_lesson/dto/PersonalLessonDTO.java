package com.kosa.jungdoin.lesson.personal_lesson.dto;

import com.kosa.jungdoin.common.Process;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PersonalLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String trainerName;
    private String title;
    private Integer price;
    private String content;
    private String location;
    private BigDecimal lat;
    private BigDecimal lng;
    private String category;
    private Process process;
}
