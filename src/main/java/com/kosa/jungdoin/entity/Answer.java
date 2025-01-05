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

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers")
public class Answer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long answerId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@ManyToOne
	@JoinColumn(name = "question_seq", nullable = false)
	private Question question;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
}