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
@Table(name = "media")
public class Media extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "media_seq")
	private Long mediaSeq;
	@ManyToOne
	@JoinColumn(name = "media_type_code", nullable = false)
	private MediaType mediaTypeCode;
	@Column(name = "resource_id", nullable = false)
	private Long resourceId;
	@Column(name = "url", nullable = false, unique = true)
	private String url;
}