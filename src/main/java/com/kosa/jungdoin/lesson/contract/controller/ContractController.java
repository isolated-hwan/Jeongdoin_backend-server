package com.kosa.jungdoin.lesson.contract.controller;

import com.kosa.jungdoin.lesson.apply.dto.ApplyRequestListDTO;
import com.kosa.jungdoin.lesson.contract.dto.ContractRequestDTO;
import com.kosa.jungdoin.lesson.contract.dto.ParticipantDTO;
import com.kosa.jungdoin.lesson.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<String> createContract(@RequestBody ContractRequestDTO requestDTO) {
        try {
            contractService.createContract(requestDTO);
            return ResponseEntity.ok("계약이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/participants")
    public ResponseEntity<List<ParticipantDTO>> getParticipants(@RequestParam("lessonId") Long lessonId,
                                                                @RequestParam String lessonCategoryCode) {
        return ResponseEntity.ok(contractService.getParticipants(lessonId, lessonCategoryCode));
    }
}
