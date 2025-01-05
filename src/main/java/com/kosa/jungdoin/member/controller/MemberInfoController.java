package com.kosa.jungdoin.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.jungdoin.member.dto.MemberInfoDto;
import com.kosa.jungdoin.member.service.MemberInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberInfoController {

	private final MemberInfoService memberInfoService;

	@GetMapping("/info")
	public ResponseEntity<MemberInfoDto> getMemberInfo(@RequestParam(name = "username") String username) {
		MemberInfoDto memberInfo = memberInfoService.getMemberInfo(username);
		log.info("memberInfo: {}", memberInfo);
		return ResponseEntity.ok(memberInfo);
	}

	@PutMapping("/update")
	public ResponseEntity<Void> updateMemberInfo(@RequestBody MemberInfoDto memberInfoDto) {
		memberInfoService.updateMemberInfo(memberInfoDto);
		log.info("memberInfo수정: {}", memberInfoDto);
		return ResponseEntity.ok().build();
	}
}
