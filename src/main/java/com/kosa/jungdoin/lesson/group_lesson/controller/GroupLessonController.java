package com.kosa.jungdoin.lesson.group_lesson.controller;

import com.kosa.jungdoin.common.exception.UnauthorizedException;
import com.kosa.jungdoin.lesson.group_lesson.dto.GroupLessonDTO;
import com.kosa.jungdoin.lesson.group_lesson.service.GroupLessonService;
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
@RequestMapping("/api/group-lesson")
@RequiredArgsConstructor
public class GroupLessonController {

    private final GroupLessonService groupLessonService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody GroupLessonDTO requestDTO) {
        try {
            groupLessonService.createLesson(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> get(@PathVariable Long lessonId) {
        try {
            GroupLessonDTO resultDTO = groupLessonService.getLesson(lessonId);
            return ResponseEntity.ok().body(resultDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<GroupLessonDTO> allLessons = groupLessonService.getAllLessons();
        if (allLessons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(allLessons);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody GroupLessonDTO requestDTO) {
        try {
            GroupLessonDTO updatedLesson = groupLessonService.updateLesson(requestDTO);
            return ResponseEntity.ok().body(updatedLesson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> patch(@RequestBody GroupLessonDTO requestDTO) {
        try {
            GroupLessonDTO patchedLesson = groupLessonService.patchLesson(requestDTO);
            return ResponseEntity.ok(patchedLesson);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> delete(@PathVariable Long lessonId) {
        try {
            groupLessonService.deleteLesson(lessonId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}