package com.kosa.jungdoin.trainer.controller;

import com.kosa.jungdoin.lesson.feedback_lesson.dto.FeedbackLessonDTO;
import com.kosa.jungdoin.lesson.feedback_lesson.service.FeedbackLessonService;
import com.kosa.jungdoin.lesson.group_lesson.dto.GroupLessonDTO;
import com.kosa.jungdoin.lesson.group_lesson.service.GroupLessonService;
import com.kosa.jungdoin.lesson.online_lesson.dto.OnlineLessonDTO;
import com.kosa.jungdoin.lesson.online_lesson.service.OnlineLessonService;
import com.kosa.jungdoin.lesson.personal_lesson.dto.PersonalLessonDTO;
import com.kosa.jungdoin.lesson.personal_lesson.service.PersonalLessonService;
import com.kosa.jungdoin.trainer.dto.AllLessonsDTO;
import com.kosa.jungdoin.trainer.dto.FeedbackLessonRequestDTO;
import com.kosa.jungdoin.trainer.dto.GroupLessonRequestDTO;
import com.kosa.jungdoin.trainer.dto.OnlineLessonRequestDTO;
import com.kosa.jungdoin.trainer.dto.PersonalLessonRequestDTO;
import com.kosa.jungdoin.trainer.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final FeedbackLessonService feedbackLessonService;
    private final GroupLessonService groupLessonService;
    private final OnlineLessonService onlineLessonService;
    private final PersonalLessonService personalLessonService;
    private final TrainerService trainerService;

    @GetMapping("/lessons")
    public ResponseEntity<AllLessonsDTO> getAllLessonsByTrainerId(Long trainerId) {
        AllLessonsDTO allLessons = trainerService.getAllLessonsByTrainerId(trainerId);
        return ResponseEntity.ok(allLessons);
    }

    @GetMapping("/feedback-lessons")
    public ResponseEntity<Object> get(@ModelAttribute FeedbackLessonRequestDTO requestDTO) {
        try {
            List<FeedbackLessonDTO> lessons = feedbackLessonService.getLessonsByTrainerId(requestDTO.getTrainerId());
            if (lessons.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lessons);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/group-lessons")
    public ResponseEntity<Object> get(@ModelAttribute GroupLessonRequestDTO requestDTO) {
        try {
            List<GroupLessonDTO> lessons = groupLessonService.getLessonsByTrainerId(requestDTO.getTrainerId());
            if (lessons.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lessons);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/online-lessons")
    public ResponseEntity<Object> get(@ModelAttribute OnlineLessonRequestDTO requestDTO) {
        try {
            List<OnlineLessonDTO> lessons = onlineLessonService.getLessonsByTrainerId(requestDTO.getTrainerId());
            if (lessons.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lessons);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/personal-lessons")
    public ResponseEntity<Object> get(@ModelAttribute PersonalLessonRequestDTO requestDTO) {
        try {
            List<PersonalLessonDTO> lessons = personalLessonService.getLessonsByTrainerId(requestDTO.getTrainerId());
            if (lessons.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lessons);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
