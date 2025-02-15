package com.kosa.jungdoin.lesson.contract.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContractRequestDTO {
    private Long applyId;
    private String lessonCategoryCode;
    private Long lessonId;
    private Long trainerId;
    private Long memberId;          // 신청한 회원 ID
    private Integer count;
    private LocalDate startDate;
    private LocalDate endDate;
}