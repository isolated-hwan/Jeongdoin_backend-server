package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "advertisements")
public class Advertisement extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_seq")
	private Long advertisementSeq;
	@Column(name = "partner_name", nullable = false)
	private String partnerName;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	@Column(name = "manager_name", nullable = false)
	private String managerName;
	@Column(name = "manager_phone", nullable = false)
	private String managerPhone;
	@Column(name = "manager_email", nullable = false)
	private String managerEmail;
}
