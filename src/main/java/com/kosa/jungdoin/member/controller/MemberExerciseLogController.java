package com.kosa.jungdoin.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.jungdoin.member.dto.MemberExerciseLogDto;
import com.kosa.jungdoin.member.service.MemberExerciseLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exercise-logs")
@RequiredArgsConstructor
public class MemberExerciseLogController {

	private final MemberExerciseLogService memberExerciseLogService;

	@PostMapping
	public ResponseEntity<MemberExerciseLogDto> createExerciseLog(@RequestBody MemberExerciseLogDto logDto) {
		MemberExerciseLogDto createLog = memberExerciseLogService.createExerciseLog(logDto);
		return ResponseEntity.ok(createLog);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MemberExerciseLogDto> updateExerciseLog(@PathVariable Long id, @RequestBody MemberExerciseLogDto logDto) {
		MemberExerciseLogDto updatedLog = memberExerciseLogService.updateExerciseLog(id, logDto);
		return ResponseEntity.ok(updatedLog);
	}

	@GetMapping
	public ResponseEntity<List<MemberExerciseLogDto>> getExerciseLogs(@RequestParam("memberId") Long memberId) {
		List<MemberExerciseLogDto> logs = memberExerciseLogService.getExerciseLogsByMemberId(memberId);
		return ResponseEntity.ok(logs);
	}

}