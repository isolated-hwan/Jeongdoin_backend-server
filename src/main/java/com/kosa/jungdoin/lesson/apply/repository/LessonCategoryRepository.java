package com.kosa.jungdoin.lesson.apply.repository;

import com.kosa.jungdoin.entity.LessonCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonCategoryRepository extends JpaRepository<LessonCategory, String> {
}
