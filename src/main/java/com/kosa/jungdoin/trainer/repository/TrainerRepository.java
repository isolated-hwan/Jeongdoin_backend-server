package com.kosa.jungdoin.trainer.repository;

import com.kosa.jungdoin.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
