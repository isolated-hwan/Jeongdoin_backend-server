package com.kosa.jungdoin.lesson.feedback_lesson.repository;

import com.kosa.jungdoin.entity.FeedbackLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackLessonRepository extends JpaRepository<FeedbackLesson, Long> {
    List<FeedbackLesson> findFeedbackLessonsByTrainerMemberId(Long trainerId);
}
