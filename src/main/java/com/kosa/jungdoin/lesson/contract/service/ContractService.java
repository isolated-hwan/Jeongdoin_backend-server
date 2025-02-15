package com.kosa.jungdoin.lesson.contract.service;

import com.kosa.jungdoin.common.Process;
import com.kosa.jungdoin.entity.*;
import com.kosa.jungdoin.lesson.apply.repository.ApplyLessonRepository;
import com.kosa.jungdoin.lesson.apply.repository.LessonCategoryRepository;
import com.kosa.jungdoin.lesson.contract.dto.ContractRequestDTO;
import com.kosa.jungdoin.lesson.contract.dto.ParticipantDTO;
import com.kosa.jungdoin.lesson.contract.repository.ContractRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ApplyLessonRepository applyLessonRepository;
    private final LessonCategoryRepository lessonCategoryRepository;
    private final TrainerRepository trainerRepository;

    public void createContract(ContractRequestDTO requestDTO) {
        ApplyLesson applyLesson = applyLessonRepository.findById(requestDTO.getApplyId())
                .orElseThrow(() -> new IllegalArgumentException("신청 정보를 찾을 수 없습니다."));

        LessonCategory lessonCategory = lessonCategoryRepository.findById(requestDTO.getLessonCategoryCode())
                .orElseThrow(() -> new IllegalArgumentException("수업 카테고리를 찾을 수 없습니다."));

        Trainer trainer = trainerRepository.findById(requestDTO.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));

        // 상태 변경 메서드 호출 대신 직접 업데이트
        applyLessonRepository.approveApplication(applyLesson.getApplyLessonId());

        Contract contract = Contract.builder()
                .applyLesson(applyLesson)
                .lessonCategoryCode(lessonCategory)
                .trainer(trainer)
                .count(requestDTO.getCount())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .process(Process.IN_PROGRESS)
                .build();

        contractRepository.save(contract);
    }

    @Transactional(readOnly = true)
    public List<ParticipantDTO> getParticipants(Long lessonId, String lessonCategoryCode) {
        List<Contract> contracts = contractRepository.findByLessonIdAndLessonCategoryCode(
                lessonId,
                lessonCategoryCode
        );

        return contracts.stream()
                .map(contract -> {
                    Member member = contract.getApplyLesson().getMember();
                    return ParticipantDTO.builder()
                            .memberId(member.getMemberId())
                            .memberName(member.getUsername())
                            .memberPhone(member.getPhone())
                            .count(contract.getCount())
                            .startDate(contract.getStartDate())
                            .endDate(contract.getEndDate())
                            .status(contract.getProcess().name())
                            .build();
                })
                .collect(Collectors.toList());
    }
}