package com.kosa.jungdoin.admin.controller;

import com.kosa.jungdoin.admin.service.TrainerApplicationService;
import com.kosa.jungdoin.member.dto.TrainerApplicationDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final TrainerApplicationService trainerApplicationService;

    @GetMapping("/trainer-applications")
    public ResponseEntity<Object> getAllTrainerApplications() {
        List<TrainerApplicationDTO> applicationList = trainerApplicationService.getAll();
        if(applicationList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationList);
    }

    @PatchMapping("/trainer-applications/{applicationId}/approve")
    public ResponseEntity<Object> approveTrainerApplication(@PathVariable Long applicationId) {
        trainerApplicationService.processTrainerApproval(applicationId);
        return ResponseEntity.ok().body("지원 신청이 승인되었습니다.");
    }

    @PatchMapping("/trainer-applications/{applicationId}/reject")
    public ResponseEntity<Object> rejectTrainerApplication(@PathVariable Long applicationId) {
        try {
            trainerApplicationService.processTrainerRejection(applicationId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("지원 신청이 거절되었습니다.");
    }

    @PatchMapping("/trainer-applications/{applicationId}/cancel")
    public ResponseEntity<Object> cancelTrainerApplication(@PathVariable Long applicationId, HttpServletRequest request) {
        trainerApplicationService.processTrainerCancellation(applicationId);
        return ResponseEntity.ok().body("지원 신청이 취소되었습니다.");
    }
}
