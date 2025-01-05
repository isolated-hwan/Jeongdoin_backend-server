package com.kosa.jungdoin.member.repository;

import com.kosa.jungdoin.entity.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
	List<TrainerProfile> findTrainerProfilesByMember_MemberId(Long memberId);
}
