package com.kosa.jungdoin.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerApplicationDTO {
    private Long applicationId;
    private Long memberId;
    private String exerciseCategoryCode;
    private List<Long> profileIdList;
}
