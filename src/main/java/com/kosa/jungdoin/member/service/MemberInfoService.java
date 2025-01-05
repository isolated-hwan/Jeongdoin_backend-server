package com.kosa.jungdoin.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.jungdoin.entity.Member;
import com.kosa.jungdoin.member.dto.MemberInfoDto;
import com.kosa.jungdoin.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberInfoDto getMemberInfo(String username) {
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		return new MemberInfoDto(member);
	}

	@Transactional
	public void updateMemberInfo(MemberInfoDto memberInfoDto) {
		Member member = memberRepository.findById(memberInfoDto.getMemberId())
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		Member updatedMember = Member.builder()
			.memberId(member.getMemberId())
			.memberOAuthId(member.getMemberOAuthId())
			.socialType(member.getSocialType())
			.role(member.getRole())
			.username(memberInfoDto.getUsername())
			.birth(memberInfoDto.getBirth())
			.gender(memberInfoDto.getGender())
			.phone(memberInfoDto.getPhone())
			.address(memberInfoDto.getAddress())
			.tall(memberInfoDto.getTall())
			.bodyWeight(memberInfoDto.getBodyWeight())
			.point(member.getPoint())
			.trial(member.getTrial())
			.build();

		memberRepository.save(updatedMember);
	}
}
