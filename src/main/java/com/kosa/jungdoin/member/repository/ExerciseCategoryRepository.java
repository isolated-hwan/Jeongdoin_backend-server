package com.kosa.jungdoin.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.jungdoin.entity.ExerciseCategory;

public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, String> {
}