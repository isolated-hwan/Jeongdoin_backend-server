package com.kosa.jungdoin.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyWeightLogDTO {
    public Long bodyWeightLogId;
    public Long memberId;
    public BigDecimal bodyWeight;
    public LocalDate dateMeasured;
}
