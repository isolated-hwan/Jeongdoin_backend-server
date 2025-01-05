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
@Table(name = "trainer_profile_categories")
public class TrainerProfileCategory {
	@Id
	@Column(name = "category_code", length = 10, nullable = false)
	private String categoryCode;
	@Column(name = "category_name", length = 20, nullable = false)
	private String categoryName;
}