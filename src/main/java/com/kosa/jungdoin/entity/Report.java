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
@Table(name = "reports")
public class Report extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long reportId;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@ManyToOne
	@JoinColumn(name = "report_type_code", nullable = false)
	private ReportType reportTypeCode;
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	@Column(name = "target_id", nullable = false)
	private Long targetId;
	@Column(name = "status", nullable = false)
	private Boolean status;
}
