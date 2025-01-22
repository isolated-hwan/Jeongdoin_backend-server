package com.kosa.jungdoin.entity;

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
@Table(name = "online_lessons")
public class OnlineLesson extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "online_lesson_id")
	private Long onlineLessonId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Trainer trainer;
	@Column(name = "title", length = 40, nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "price", nullable = false)
	private Integer price;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10, nullable = false)
	private Status status;
}
