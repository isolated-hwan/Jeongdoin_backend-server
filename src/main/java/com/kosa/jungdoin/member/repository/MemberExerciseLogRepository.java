package com.kosa.jungdoin.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.jungdoin.entity.MemberExerciseLog;

public interface MemberExerciseLogRepository extends JpaRepository<MemberExerciseLog, Long> {

	List<MemberExerciseLog> findByMemberMemberId(Long memberId);
}
