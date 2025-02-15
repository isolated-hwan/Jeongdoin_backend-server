package com.kosa.jungdoin.lesson.apply.service;

import com.kosa.jungdoin.common.Status;
import com.kosa.jungdoin.entity.*;
import com.kosa.jungdoin.lesson.apply.dto.ApplyLessonRequestDTO;
import com.kosa.jungdoin.lesson.apply.dto.ApplyLessonResponseDTO;
import com.kosa.jungdoin.lesson.apply.dto.ApplyRequestListDTO;
import com.kosa.jungdoin.lesson.apply.repository.ApplyLessonRepository;
import com.kosa.jungdoin.lesson.apply.repository.LessonCategoryRepository;
import com.kosa.jungdoin.lesson.contract.repository.ContractRepository;
import com.kosa.jungdoin.lesson.group_lesson.repository.GroupLessonRepository;
import com.kosa.jungdoin.lesson.online_lesson.repository.OnlineLessonRepository;
import com.kosa.jungdoin.lesson.personal_lesson.repository.PersonalLessonRepository;
import com.kosa.jungdoin.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyLessonService {

    private final ApplyLessonRepository applyLessonRepository;
    private final LessonCategoryRepository lessonCategoryRepository;
    private final MemberRepository memberRepository;
    private final PersonalLessonRepository personalLessonRepository;
    private final GroupLessonRepository groupLessonRepository;
    private final OnlineLessonRepository onlineLessonRepository;
    private final ContractRepository contractRepository;

    public void createApplyLesson(ApplyLessonRequestDTO requestDTO) {
        // 회원 정보 조회
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        LessonCategory lessonCategory = lessonCategoryRepository.findById(requestDTO.getLessonCategoryCode())
                .orElseThrow(() -> new IllegalArgumentException("수업 카테고리를 찾을 수 없습니다."));
        // ApplyLesson 생성
        ApplyLesson applyLesson = ApplyLesson.builder()
                .lessonId(requestDTO.getLessonId())
                .lessonCategoryCode(lessonCategory)
                .member(member)
                .status(Status.PENDING)
                .memberContent(requestDTO.getMemberContent())
                .trainerContent("")
                .build();

        applyLessonRepository.save(applyLesson);
    }

    @Transactional(readOnly = true)
    public List<ApplyLessonResponseDTO> getApplyLessonsByMemberId(Long memberId) {
        List<ApplyLesson> applyLessons = applyLessonRepository.findByMemberMemberId(memberId);

        return applyLessons.stream().map(applyLesson -> {
            String lessonType = getLessonType(applyLesson.getLessonCategoryCode().getLessonCategoryCode());
            ApplyLessonResponseDTO.ApplyLessonResponseDTOBuilder builder = ApplyLessonResponseDTO.builder()
                    .lessonId(applyLesson.getLessonId())
                    .lessonType(lessonType)
                    .status(applyLesson.getStatus().getStatus())
                    .memberContent(applyLesson.getMemberContent())
                    .trainerContent(applyLesson.getTrainerContent());
            // Contract 정보 조회
            Contract contract = contractRepository.findByApplyLesson(applyLesson).orElse(null);
            if (contract != null) {
                builder
                        .count(contract.getCount())
                        .startDate(contract.getStartDate())
                        .endDate(contract.getEndDate());
            }
            // 레슨 타입에 따라 해당하는 레슨 정보 조회
            switch (lessonType) {
                case "개인 레슨":
                    PersonalLesson personalLesson = personalLessonRepository.findById(applyLesson.getLessonId())
                            .orElse(null);
                    if (personalLesson != null) {
                        Trainer trainer = personalLesson.getTrainer();
                        builder
                                .title(personalLesson.getTitle())
                                .content(personalLesson.getContent())
                                .price(personalLesson.getPrice())
                                .location(personalLesson.getLocation())
                                .lat(personalLesson.getLat())
                                .lng(personalLesson.getLng())
                                .trainerName(trainer.getBaseMember().getUsername())
                                .exerciseCategory(trainer.getExerciseCategory().getCategoryName());
                    }
                    break;
                case "그룹 레슨":
                    GroupLesson groupLesson = groupLessonRepository.findById(applyLesson.getLessonId())
                            .orElse(null);
                    if (groupLesson != null) {
                        Trainer trainer = groupLesson.getTrainer();
                        builder
                                .title(groupLesson.getTitle())
                                .content(groupLesson.getContent())
                                .price(groupLesson.getPrice())
                                .location(groupLesson.getLocation())
                                .lat(groupLesson.getLat())
                                .lng(groupLesson.getLng())
                                .maxCnt(groupLesson.getMaxCnt())
                                .recruitmentStart(groupLesson.getStartDate())
                                .recruitmentEnd(groupLesson.getStartEnd())
                                .trainerName(trainer.getBaseMember().getUsername())
                                .exerciseCategory(trainer.getExerciseCategory().getCategoryName());
                    }
                    break;
                case "온라인 레슨":
                    OnlineLesson onlineLesson = onlineLessonRepository.findById(applyLesson.getLessonId())
                            .orElse(null);
                    if (onlineLesson != null) {
                        Trainer trainer = onlineLesson.getTrainer();
                        builder
                                .title(onlineLesson.getTitle())
                                .content(onlineLesson.getContent())
                                .price(onlineLesson.getPrice())
                                .trainerName(trainer.getBaseMember().getUsername())
                                .exerciseCategory(trainer.getExerciseCategory().getCategoryName());
                    }
                    break;
            }

            return builder.build();
        }).collect(Collectors.toList());
    }

    private String getLessonType(String categoryCode) {
        switch (categoryCode) {
            case "00":
                return "개인 레슨";
            case "01":
                return "그룹 레슨";
            case "02":
                return "온라인 레슨";
            default:
                return "알 수 없음";
        }
    }

    @Transactional(readOnly = true)
    public List<ApplyRequestListDTO> getPendingRequests(Long lessonId, String lessonCategoryCode) {
        List<ApplyLesson> pendingApplies = applyLessonRepository
                .findByLessonIdAndLessonCategoryCodeLessonCategoryCodeAndStatus(
                        lessonId,
                        lessonCategoryCode,
                        Status.PENDING
                );

        return pendingApplies.stream()
                .map(apply -> ApplyRequestListDTO.builder()
                        .applyId(apply.getApplyLessonId())
                        .memberId(apply.getMember().getMemberId())
                        .memberName(apply.getMember().getUsername())
                        .memberContent(apply.getMemberContent())
                        .memberPhone(apply.getMember().getPhone())
                        .build())
                .collect(Collectors.toList());
    }

    public void rejectApplication(Long applyId, String trainerContent) {
        ApplyLesson applyLesson = applyLessonRepository.findById(applyId)
                .orElseThrow(() -> new IllegalArgumentException("신청 정보를 찾을 수 없습니다."));
        if (applyLesson.getStatus() != Status.PENDING) {
            throw new IllegalStateException("대기 중인 신청만 거절할 수 있습니다.");
        }
        applyLessonRepository.rejectApplication(applyId, Status.REJECTED, trainerContent);
    }
}