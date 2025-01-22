package com.kosa.jungdoin.entity;

import com.kosa.jungdoin.common.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "apply_lessons")
public class ApplyLesson extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_lesson_id")
	private Long applyLessonId;

	@ManyToOne
	@JoinColumn(name = "lesson_category_code")
	private LessonCategory lessonCategoryCode;

	@Column(name = "lesson_id")
	private Long lessonId;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10, nullable = false)
	private Status status;

	@Column(name = "member_content", length = 2000, nullable = false)
	private String memberContent;

	@Column(name = "trainer_content", length = 2000, nullable = false)
	private String trainerContent;
}
