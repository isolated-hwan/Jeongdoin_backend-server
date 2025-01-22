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

import java.time.LocalDate;

import com.kosa.jungdoin.common.Status;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback_requests")
public class FeedbackRequest extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedback_request_id")
	private Long feedbackRequestId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@ManyToOne
	@JoinColumn(name = "feedback_lesson_id", nullable = false)
	private FeedbackLesson feedbackLesson;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "expire_date", nullable = false)
	private LocalDate expireDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private Status status;
}