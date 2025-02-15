package com.kosa.jungdoin.lesson.apply.dto;

import com.kosa.jungdoin.common.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ApplyLessonResponseDTO {
    // 기본 목록 표시용 정보
    private Long lessonId;
    private String lessonType;
    private String exerciseCategory;  // 트레이너의 운동 종목
    private String title;
    private String trainerName;
    private String status;

    // 레슨 상세 정보
    private String content;          // 레슨 설명
    private Integer price;
    private String location;         // 개인/그룹 레슨용
    private BigDecimal lat;          // 개인/그룹 레슨용
    private BigDecimal lng;          // 개인/그룹 레슨용
    private Integer maxCnt;          // 그룹 레슨용
    private LocalDate recruitmentStart;
    private LocalDate recruitmentEnd;

    // 신청 관련 정보
    private String memberContent;    // 문의 내용
    private String trainerContent;   // 트레이너 답변

    private Integer count;
    private LocalDate startDate;
    private LocalDate endDate;
}
