package com.kosa.jungdoin.lesson.apply.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyLessonRequestDTO {
    private Long lessonId;
    private String lessonCategoryCode;
    private Long memberId;  // 로그인한 회원의 ID
    private String memberContent;
}