package com.kosa.jungdoin.entity;

import java.time.LocalDate;

import com.kosa.jungdoin.common.Process;
import com.kosa.jungdoin.common.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "contracts")
public class Contract extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private Long contractId;

	@ManyToOne
	@JoinColumn(name = "lesson_category_code", nullable = false)
	private LessonCategory lessonCategory;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Trainer trainer;

	@ManyToOne
	@JoinColumn(name = "apply_lesson_id", nullable = false)
	private ApplyLesson applyLesson;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "count", nullable = false)
	private Integer count;

	@Enumerated(EnumType.STRING)
	@Column(name = "process", length = 20, nullable = false)
	private Process process;
}
