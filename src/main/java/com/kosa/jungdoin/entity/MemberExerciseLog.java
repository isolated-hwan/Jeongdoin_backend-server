package com.kosa.jungdoin.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_exercise_logs")
public class MemberExerciseLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Long logId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@Column(name = "workout_date", nullable = false)
	private LocalDate workoutDate;
	@Column(name = "workout_start_time")
	private LocalTime workoutStartTime;
	@Column(name = "workout_end_time")
	private LocalTime workoutEndTime;
	@Column(name = "content", length = 2000)
	private String content;
	@Column(name = "body_weight", precision = 4, scale = 1)
	private BigDecimal bodyWeight;
	@Column(name = "carb")
	private Integer carb;
	@Column(name = "protein")
	private Integer protein;
	@Column(name = "fat")
	private Integer fat;
}
