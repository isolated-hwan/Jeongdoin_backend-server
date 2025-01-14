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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "lesson_category_code")
	private LessonCategory lessonCategoryCode;

	@Column(name = "lesson_id")
	private Long lessonId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "state", length = 10, nullable = false)
	private String status;

	@Column(name = "member_content", length = 2000, nullable = false)
	private String memberContent;

	@Column(name = "trainer_content", length = 2000, nullable = false)
	private String trainerContent;
}
