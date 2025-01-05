package com.kosa.jungdoin.entity;

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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_lesson_logs")
public class TrainerLessonLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lesson_log_id")
	private Long lessonLogId;
	@ManyToOne
	@JoinColumn(name = "contract_id", nullable = false)
	private Contract contract;
	@Column(name = "lesson_date", nullable = false)
	private LocalDate lessonDate;
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "state", length = 20, nullable = false)
	private String state;
}
