package com.kosa.jungdoin.lesson.personal_lesson.repository;

import com.kosa.jungdoin.entity.PersonalLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalLessonRepository extends JpaRepository<PersonalLesson, Long> {
    List<PersonalLesson> findPersonalLessonsByTrainerMemberId(Long trainerId);
}
