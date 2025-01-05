package com.kosa.jungdoin.admin.service;

import com.kosa.jungdoin.admin.repository.TrainerApplicationHistoryRepository;
import com.kosa.jungdoin.common.Role;
import com.kosa.jungdoin.entity.BaseMember;
import com.kosa.jungdoin.entity.ExerciseCategory;
import com.kosa.jungdoin.entity.Member;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.entity.TrainerApplication;
import com.kosa.jungdoin.entity.TrainerApplicationHistory;
import com.kosa.jungdoin.entity.TrainerProfile;
import com.kosa.jungdoin.member.dto.TrainerApplicationDTO;
import com.kosa.jungdoin.member.repository.BaseMemberRepository;
import com.kosa.jungdoin.member.repository.ExerciseCategoryRepository;
import com.kosa.jungdoin.member.repository.MemberRepository;
import com.kosa.jungdoin.admin.repository.TrainerApplicationRepository;
import com.kosa.jungdoin.member.repository.TrainerProfileRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TrainerApplicationService {

    private final TrainerApplicationRepository trainerApplicationRepository;
    private final TrainerApplicationHistoryRepository trainerApplicationHistoryRepository;
    private final TrainerProfileRepository trainerProfileRepository;
    private final MemberRepository memberRepository;
    private final BaseMemberRepository baseMemberRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final TrainerRepository trainerRepository;

    public TrainerApplication saveTrainerApplication(TrainerApplicationDTO requestDTO) {
        if (requestDTO.getProfileIdList().isEmpty()) {
            throw new IllegalArgumentException("At least one profile must be provided");
        }

        List<TrainerProfile> profiles = requestDTO.getProfileIdList().stream()
                .map(trainerProfileRepository::findById)
                .map(optional -> optional.orElseThrow(() -> new IllegalArgumentException("Profile not found")))
                .toList();

        Optional<Member> member = memberRepository.findById(requestDTO.getMemberId());
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member not found");
        }

        ExerciseCategory exerciseCategory = exerciseCategoryRepository.findById(requestDTO.getExerciseCategoryCode()).orElseThrow(() -> {
            throw new IllegalArgumentException("Exercise category not found");
        });

        return trainerApplicationRepository.save(
                TrainerApplication.builder()
                        .exerciseCategory(exerciseCategory)
                        .trainerProfiles(profiles)
                        .status("PENDING")
                        .member(member.get())
                        .build());
    }

    public void processTrainerApproval(Long applicationId) {
        TrainerApplication application = trainerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        BaseMember member = application.getMember();

        Member savedMember = baseMemberRepository.save(
                Member.builder()
                        .memberId(member.getMemberId())
                        .socialType(member.getSocialType())
                        .memberOAuthId(member.getMemberOAuthId())
                        .birth(member.getBirth())
                        .role(Role.TRAINER)
                        .phone(member.getPhone())
                        .trial(member.getTrial())
                        .point(member.getPoint())
                        .gender(member.getGender())
                        .username(member.getUsername())
                        .build()
        );

        trainerRepository.save(
                Trainer.builder()
                        .exerciseCategory(
                                exerciseCategoryRepository.
                                        findById(application.getExerciseCategory().getExerciseCategoryCode())
                                        .orElseThrow(() -> new IllegalArgumentException("Category not found")))
                        .baseMember(savedMember)
                        .build());


        TrainerApplication updatedApplication =
                application.toBuilder()
                        .status("APPROVED")
                        .build();

        trainerApplicationRepository.save(updatedApplication);

        saveApplicationHistory(updatedApplication, "APPROVED");

    }

    public void processTrainerRejection(Long applicationId) {
        TrainerApplication application = trainerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        TrainerApplication updatedApplication =
                application.toBuilder()
                        .status("REJECTED")
                        .build();

        trainerApplicationRepository.save(updatedApplication);

        saveApplicationHistory(updatedApplication, "REJECTED");
    }

    public void processTrainerCancellation(Long applicationId) {
        TrainerApplication application = trainerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        TrainerApplication updatedApplication =
                application.toBuilder()
                        .status("CANCELLED")
                        .build();

        trainerApplicationRepository.save(updatedApplication);

        saveApplicationHistory(updatedApplication, "CANCELLED");
    }

    private void saveApplicationHistory(TrainerApplication application, String status) {
        TrainerApplicationHistory history = TrainerApplicationHistory.builder()
                .trainerApplication(application)
                .status(status)
                .changeDate(LocalDateTime.now())
                .changedBy("Admin")
                .build();

        trainerApplicationHistoryRepository.save(history);
    }

    public Boolean isExistsApplication(Long applicationId) {
        return trainerApplicationRepository.existsById(applicationId);
    }

    public List<TrainerApplicationDTO> getAll() {
        return trainerApplicationRepository.findAll().stream()
                .map(e -> TrainerApplicationDTO.builder()
                        .applicationId(e.getId())
                        .exerciseCategoryCode(e.getExerciseCategory().getExerciseCategoryCode())
                        .profileIdList(e.getTrainerProfiles().stream().map(TrainerProfile::getTrainerProfileId)
                                .toList())
                        .memberId(e.getMember().getMemberId())
                        .build()
                )
                .toList();
    }
}
