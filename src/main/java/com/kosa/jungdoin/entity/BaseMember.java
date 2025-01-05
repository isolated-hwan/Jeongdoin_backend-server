package com.kosa.jungdoin.entity;

import com.kosa.jungdoin.common.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseMember extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;
	@Column(name = "member_oauth_id", nullable = false, unique = true)
	private String memberOAuthId;
	@Column(name = "social_type", length = 20, nullable = false)
	private String socialType;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 10, nullable = false)
	private Role role;
	@Column(name = "username", length = 150, nullable = false)
	private String username;
	@Column(name = "birth", length = 10)
	private LocalDate birth;
	@Column(name = "gender", length = 1)
	private String gender;
	@Column(name = "phone", length = 13)
	private String phone;
	@Column(name = "point")
	private Integer point;
	@Column(name = "trial")
	private Boolean trial;
}