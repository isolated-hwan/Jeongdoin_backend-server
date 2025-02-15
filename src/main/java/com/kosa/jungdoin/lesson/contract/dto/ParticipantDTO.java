package com.kosa.jungdoin.lesson.contract.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ParticipantDTO {
    private Long memberId;
    private String memberName;
    private String memberPhone;
    private Integer count;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
