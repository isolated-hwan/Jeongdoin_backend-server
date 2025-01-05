package com.kosa.jungdoin.member.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kosa.jungdoin.entity.Member;
import com.kosa.jungdoin.entity.MemberExerciseLog;
import com.kosa.jungdoin.member.repository.MemberExerciseLogRepository;
import com.kosa.jungdoin.member.dto.MemberExerciseLogDto;
import com.kosa.jungdoin.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberExerciseLogService {

	private final MemberExerciseLogRepository memberExerciseLogRepository;
	private final MemberRepository memberRepository;
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm[:ss]");

	public MemberExerciseLogDto createExerciseLog(MemberExerciseLogDto logDTO) {
		Member member = memberRepository.findById(logDTO.getMemberId())
			.orElseThrow(() -> new RuntimeException("Member not found"));

		MemberExerciseLog log = MemberExerciseLog.builder()
			.member(member)
			.workoutDate(logDTO.getWorkoutDate())
			.workoutStartTime(logDTO.getWorkoutStartTime())
			.workoutEndTime(logDTO.getWorkoutEndTime())
			.content(logDTO.getContent())
			.bodyWeight(logDTO.getBodyWeight())
			.carb(logDTO.getCarb())
			.protein(logDTO.getProtein())
			.fat(logDTO.getFat())
			.build();

		MemberExerciseLog savedLog = memberExerciseLogRepository.save(log);
		return convertToDTO(savedLog);
	}

	public MemberExerciseLogDto updateExerciseLog(Long id, MemberExerciseLogDto logDTO) {
		MemberExerciseLog existingLog = memberExerciseLogRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Exercise log not found"));

		MemberExerciseLog updatedLog = MemberExerciseLog.builder()
			.logId(existingLog.getLogId())
			.member(existingLog.getMember())
			.workoutDate(logDTO.getWorkoutDate())
			.workoutStartTime(logDTO.getWorkoutStartTime())
			.workoutEndTime(logDTO.getWorkoutEndTime())
			.content(logDTO.getContent())
			.bodyWeight(logDTO.getBodyWeight())
			.carb(logDTO.getCarb())
			.protein(logDTO.getProtein())
			.fat(logDTO.getFat())
			.build();

		MemberExerciseLog savedLog = memberExerciseLogRepository.save(updatedLog);
		return convertToDTO(savedLog);
	}

	public List<MemberExerciseLogDto> getExerciseLogsByMemberId(Long memberId) {
		List<MemberExerciseLog> logs = memberExerciseLogRepository.findByMemberMemberId(memberId);
		return logs.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public void deleteExerciseLog(Long logId) {
		memberExerciseLogRepository.deleteById(logId);
	}

	private MemberExerciseLogDto convertToDTO(MemberExerciseLog log) {
		return MemberExerciseLogDto.builder()
			.logId(log.getLogId())
			.memberId(log.getMember().getMemberId())
			.workoutDate(log.getWorkoutDate())
			.workoutStartTime(log.getWorkoutStartTime())
			.workoutEndTime(log.getWorkoutEndTime())
			.content(log.getContent())
			.bodyWeight(log.getBodyWeight())
			.carb(log.getCarb())
			.protein(log.getProtein())
			.fat(log.getFat())
			.build();
	}
}