package com.kosa.jungdoin.lesson.group_lesson.repository;

import com.kosa.jungdoin.entity.GroupLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupLessonRepository extends JpaRepository<GroupLesson, Long> {
    List<GroupLesson> findGroupLessonsByTrainerMemberId(Long trainerId);
}
