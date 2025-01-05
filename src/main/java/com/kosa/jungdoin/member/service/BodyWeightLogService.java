package com.kosa.jungdoin.member.service;

import com.kosa.jungdoin.entity.BodyWeightLog;
import com.kosa.jungdoin.entity.Member;
import com.kosa.jungdoin.member.dto.BodyWeightLogDTO;
import com.kosa.jungdoin.member.repository.BodyWeightLogRepository;
import com.kosa.jungdoin.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BodyWeightLogService {
    private final BodyWeightLogRepository bodyWeightLogRepository;
    private final MemberRepository memberRepository;

    //    @RequireAuthorization
    @Transactional(readOnly = true)
    public List<BodyWeightLogDTO> getAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        return bodyWeightLogRepository.findAllByMember(member)
                .stream().map(e -> BodyWeightLogDTO.builder()
                        .logId(e.getId())
                        .memberId(e.getMember().getMemberId())
                        .bodyWeight(e.getBodyWeight())
                        .dateMeasured(e.getDateMeasured())
                        .build())
                .toList();
    }

    public BodyWeightLogDTO saveMemberBodyWeight(BodyWeightLogDTO requestDTO)
            throws IllegalArgumentException, OptimisticLockingFailureException {

        Optional<BodyWeightLog> logOptional = bodyWeightLogRepository.findByDateMeasured(requestDTO.getDateMeasured());
        if (logOptional.isPresent()) {
            return null;
        }

        Member member = Member.builder().memberId(requestDTO.getMemberId()).build();
        BodyWeightLog saved = bodyWeightLogRepository.save(
                BodyWeightLog.builder()
                        .bodyWeight(requestDTO.bodyWeight)
                        .member(member)
                        .dateMeasured(requestDTO.dateMeasured)
                        .build());

        return BodyWeightLogDTO.builder()
                .memberId(saved.getMember().getMemberId())
                .bodyWeight(saved.getBodyWeight())
                .dateMeasured(saved.getDateMeasured())
                .build();
    }

    //    @RequireAuthorization
    public Boolean removeLog(BodyWeightLogDTO requestDTO)
            throws IllegalArgumentException, OptimisticLockingFailureException {
        if (bodyWeightLogRepository.findById(requestDTO.logId).isEmpty()) {
            return false;
        }

        bodyWeightLogRepository.delete(
                BodyWeightLog.builder()
                        .id(requestDTO.logId)
                        .build()
        );
        return true;
    }

    //    @RequireAuthorization
    public BodyWeightLogDTO update(BodyWeightLogDTO requestDTO)
            throws IllegalArgumentException, OptimisticLockingFailureException {
        Member member = Member.builder().memberId(requestDTO.getMemberId()).build();
        BodyWeightLog saved = bodyWeightLogRepository.save(
                BodyWeightLog.builder()
                        .id(requestDTO.logId)
                        .member(member)
                        .bodyWeight(requestDTO.bodyWeight)
                        .dateMeasured(requestDTO.dateMeasured)
                        .build()
        );
        return BodyWeightLogDTO.builder()
                .bodyWeight(saved.getBodyWeight())
                .dateMeasured(saved.getDateMeasured())
                .build();
    }
}
