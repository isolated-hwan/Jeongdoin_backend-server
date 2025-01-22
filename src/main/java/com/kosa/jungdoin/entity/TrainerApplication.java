package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import com.kosa.jungdoin.common.Status;

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
    private Long trainerApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private BaseMember member;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private Status status; // 예: "PENDING", "APPROVED", "REJECTED"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_category_code")
    private ExerciseCategory exerciseCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainerApplication")
    private List<TrainerProfile> trainerProfiles;
}
