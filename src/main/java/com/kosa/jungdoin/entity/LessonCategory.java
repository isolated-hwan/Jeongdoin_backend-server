package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "lesson_categories")
public class LessonCategory {
	@Id
	@Column(name = "lesson_category_code", length = 10, nullable = false)
	private String lessonCategoryCode;
	@Column(name = "lesson_name", length = 20, nullable = false)
	private String lessonName;
}
