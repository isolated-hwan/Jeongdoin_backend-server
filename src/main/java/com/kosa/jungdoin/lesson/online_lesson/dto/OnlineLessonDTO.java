package com.kosa.jungdoin.lesson.online_lesson.dto;

import com.kosa.jungdoin.common.Process;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnlineLessonDTO {
    private Long lessonId;
    private Long trainerId;
    private String trainerName;
    private String title;
    private String content;
    private Integer price;
    private String category;
    private Process process;
}