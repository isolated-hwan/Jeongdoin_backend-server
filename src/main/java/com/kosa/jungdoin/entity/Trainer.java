package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "trainers")
public class Trainer {
	@Id
	@Column(name = "member_id")
	private Long memberId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "member_id")
	private BaseMember baseMember;

	@ManyToOne
	@JoinColumn(name = "exercise_category_code", nullable = false)
	private ExerciseCategory exerciseCategory;
}
