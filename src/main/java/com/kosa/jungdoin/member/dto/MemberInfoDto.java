package com.kosa.jungdoin.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.kosa.jungdoin.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
	private Long memberId;
	private String username;
	private LocalDate birth;
	private String gender;
	private String phone;
	private String address;
	private BigDecimal tall;
	private BigDecimal bodyWeight;

	// Member 엔티티를 받아서 DTO로 변환하는 생성자
	public MemberInfoDto(Member member) {
		this.memberId = member.getMemberId();
		this.username = member.getUsername();
		this.birth = member.getBirth();
		this.gender = member.getGender();
		this.phone = member.getPhone();
		this.address = member.getAddress();
		this.tall = member.getTall();
		this.bodyWeight = member.getBodyWeight();
	}
}
