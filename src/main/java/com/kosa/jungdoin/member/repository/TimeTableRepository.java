package com.kosa.jungdoin.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.jungdoin.entity.TimeTable;
import com.kosa.jungdoin.entity.Trainer;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
	List<TimeTable> findByTrainer(Trainer trainer);
}
