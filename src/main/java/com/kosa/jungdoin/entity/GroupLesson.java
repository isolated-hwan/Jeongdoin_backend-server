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

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_lessons")
public class GroupLesson extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_lesson_id")
	private Long groupLessonId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Trainer trainer;
	@Column(name = "max_cnt", nullable = false)
	private Integer maxCnt;
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	@Column(name = "start_end", nullable = false)
	private LocalDate startEnd;
	@Column(name = "done", nullable = false)
	private Boolean done;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "title", length = 40, nullable = false)
	private String title;
	@Column(name = "price", nullable = false)
	private Integer price;
	@Column(name = "location", nullable = false)
	private String location;
	@Column(name = "lat", precision = 17, scale = 15, nullable = false)
	private BigDecimal lat;
	@Column(name = "lng", precision = 17, scale = 14, nullable = false)
	private BigDecimal lng;
}