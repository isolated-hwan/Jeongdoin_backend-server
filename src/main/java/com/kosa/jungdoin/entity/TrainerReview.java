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

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_reviews")
public class TrainerReview extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trainer_review_seq")
	private Long trainerReviewSeq;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
	private Trainer trainer;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
	private Member member;
	@Column(name = "satisfaction", precision = 2, scale = 1, nullable = false)
	private BigDecimal satisfaction;
	@Column(name = "expertise", precision = 2, scale = 1, nullable = false)
	private BigDecimal expertise;
	@Column(name = "kindness", precision = 2, scale = 1, nullable = false)
	private BigDecimal kindness;
	@Column(name = "punctuality", precision = 2, scale = 1, nullable = false)
	private BigDecimal punctuality;
	@Column(name = "passion", precision = 2, scale = 1, nullable = false)
	private BigDecimal passion;
	@Column(name = "comment", length = 2000, nullable = false)
	private String comment;
}
