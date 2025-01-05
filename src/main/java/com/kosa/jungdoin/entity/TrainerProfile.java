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

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_profiles")
public class TrainerProfile extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trainer_profile_id")
	private Long trainerProfileId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private BaseMember member;
	@ManyToOne
	@JoinColumn(name = "category_code", nullable = false)
	private TrainerProfileCategory categoryCode;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	@Column(name = "detail", nullable = false)
	private String detail;
}
