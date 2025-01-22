package com.kosa.jungdoin.entity;

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

import java.math.BigDecimal;

import com.kosa.jungdoin.common.Status;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personal_lessons")
public class PersonalLesson extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_lesson_id")
	private Long personalLessonId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Trainer trainer;
	@Column(name = "price", nullable = false)
	private Integer price;
	@Column(name = "title", length = 40, nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "location", nullable = false)
	private String location;
	@Column(name = "lat", precision = 17, scale = 15, nullable = false)
	private BigDecimal lat;
	@Column(name = "lng", precision = 17, scale = 14, nullable = false)
	private BigDecimal lng;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10, nullable = false)
	private Status status;
}