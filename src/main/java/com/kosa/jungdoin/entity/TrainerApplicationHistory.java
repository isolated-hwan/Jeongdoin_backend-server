package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_application_histories")
public class TrainerApplicationHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_application_id", nullable = false)
    private TrainerApplication trainerApplication;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // 예: "PENDING", "APPROVED", "REJECTED"

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @Column(name = "changed_by", length = 50)
    private String changedBy; // 변경을 수행한 사용자 또는 시스템
}
