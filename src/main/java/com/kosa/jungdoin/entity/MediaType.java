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
@Table(name = "media_types")
public class MediaType {
	@Id
	@Column(name = "media_type_code", length = 10, nullable = false)
	private String mediaTypeCode;
	@Column(name = "media_type_name", length = 20, nullable = false)
	private String mediaTypeName;
}
