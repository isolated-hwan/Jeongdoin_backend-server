package com.kosa.jungdoin.lesson.group_lesson.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GroupLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String trainerName;
    private Integer maxCnt;
    private LocalDate startDate;
    private LocalDate startEnd;
    private Boolean done;
    private String content;
    private String title;
    private Integer price;
    private String location;
    private BigDecimal lat;
    private BigDecimal lng;
    private String category;
}