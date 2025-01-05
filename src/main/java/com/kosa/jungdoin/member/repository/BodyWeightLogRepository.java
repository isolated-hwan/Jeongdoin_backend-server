package com.kosa.jungdoin.member.repository;

import com.kosa.jungdoin.entity.BodyWeightLog;
import com.kosa.jungdoin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BodyWeightLogRepository extends JpaRepository<BodyWeightLog, Long> {
    List<BodyWeightLog> findAllByMember(Member member);

    Optional<BodyWeightLog> findByDateMeasured(LocalDate dateMeasured);
}
