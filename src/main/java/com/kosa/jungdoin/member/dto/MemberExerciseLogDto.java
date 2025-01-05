package com.kosa.jungdoin.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 이 어노테이션 추가
@AllArgsConstructor
public class MemberExerciseLogDto {
	private Long logId;
	private Long memberId;
	private LocalDate workoutDate;
	private LocalTime workoutStartTime;
	private LocalTime workoutEndTime;
	private String content;
	private BigDecimal bodyWeight;
	private Integer carb;
	private Integer protein;
	private Integer fat;
}
