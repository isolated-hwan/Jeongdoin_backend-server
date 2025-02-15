package com.kosa.jungdoin.lesson.apply.controller;

import com.kosa.jungdoin.lesson.apply.dto.ApplyLessonRequestDTO;
import com.kosa.jungdoin.lesson.apply.dto.ApplyLessonResponseDTO;
import com.kosa.jungdoin.lesson.apply.dto.ApplyRequestListDTO;
import com.kosa.jungdoin.lesson.apply.service.ApplyLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apply-lesson")
@RequiredArgsConstructor
public class ApplyLessonController {

    private final ApplyLessonService applyLessonService;

    @PostMapping
    public ResponseEntity<Object> applyLesson(@RequestBody ApplyLessonRequestDTO requestDTO) {
        try {
            applyLessonService.createApplyLesson(requestDTO);
            return ResponseEntity.ok().body("레슨 신청이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Object> getApplyLessons(@PathVariable("memberId") Long memberId) {
        try {
            List<ApplyLessonResponseDTO> lessons = applyLessonService.getApplyLessonsByMemberId(memberId);
            return ResponseEntity.ok(lessons);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ApplyRequestListDTO>> getPendingRequests(
            @RequestParam("lessonId") Long lessonId,
            @RequestParam("lessonCategoryCode") String lessonCategoryCode) {
        return ResponseEntity.ok(applyLessonService.getPendingRequests(lessonId, lessonCategoryCode));
    }

    @PatchMapping("/{applyId}/reject")
    public ResponseEntity<String> rejectApplication(
            @PathVariable("applyId") Long applyId,
            @RequestBody Map<String, String> request) {
        try {
            applyLessonService.rejectApplication(applyId, request.get("trainerContent"));
            return ResponseEntity.ok("신청이 거절되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
