package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_applications")
public class TrainerApplication extends BaseEntity {
    @Id
    @Column(name = "trainer_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private BaseMember member;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // 예: "PENDING", "APPROVED", "REJECTED"

    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseCategory exerciseCategory;

    @OneToMany(fetch = FetchType.LAZY)
    private List<TrainerProfile> trainerProfiles;
}
