package com.kosa.jungdoin.lesson.online_lesson.controller;

import com.kosa.jungdoin.common.exception.UnauthorizedException;
import com.kosa.jungdoin.lesson.online_lesson.dto.OnlineLessonDTO;
import com.kosa.jungdoin.lesson.online_lesson.service.OnlineLessonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/online-lesson")
public class OnlineLessonController {

    private final OnlineLessonService onlineLessonService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody OnlineLessonDTO requestDTO) {
        try {
            OnlineLessonDTO createdLesson = onlineLessonService.createLesson(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> get(@PathVariable Long lessonId) {
        try {
            OnlineLessonDTO resultDTO = onlineLessonService.getLesson(lessonId);
            return ResponseEntity.ok().body(resultDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<OnlineLessonDTO>> getOlineLessonsByTrainer(@PathVariable("trainerId") Long trainerId) {
        try {
            List<OnlineLessonDTO> lessons = onlineLessonService.findByTrainerId(trainerId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<OnlineLessonDTO> allLessons = onlineLessonService.getAllLessons();
        if (allLessons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(allLessons);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody OnlineLessonDTO requestDTO) {
        try {
            OnlineLessonDTO updatedLesson = onlineLessonService.updateLesson(requestDTO);
            return ResponseEntity.ok().body(updatedLesson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> patch(@RequestBody OnlineLessonDTO requestDTO) {
        try {
            OnlineLessonDTO patchedLesson = onlineLessonService.patchLesson(requestDTO);
            return ResponseEntity.ok(patchedLesson);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> delete(@PathVariable Long lessonId) {
        try {
            onlineLessonService.deleteLesson(lessonId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}