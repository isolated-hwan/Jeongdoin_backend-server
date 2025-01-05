package com.kosa.jungdoin.lesson.online_lesson.repository;

import com.kosa.jungdoin.entity.OnlineLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineLessonRepository extends JpaRepository<OnlineLesson, Long> {
    List<OnlineLesson> findOnlineLessonsByTrainerMemberId(Long trainerId);
}
