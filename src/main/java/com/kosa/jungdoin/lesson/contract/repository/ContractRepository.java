package com.kosa.jungdoin.lesson.contract.repository;

import com.kosa.jungdoin.common.Status;
import com.kosa.jungdoin.entity.ApplyLesson;
import com.kosa.jungdoin.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c " +
            "WHERE c.applyLesson.lessonId = :lessonId " +
            "AND c.applyLesson.lessonCategoryCode.lessonCategoryCode = :lessonCategoryCode")
    List<Contract> findByLessonIdAndLessonCategoryCode(
            @Param("lessonId") Long lessonId,
            @Param("lessonCategoryCode") String lessonCategoryCode
    );

    Optional<Contract> findByApplyLesson(ApplyLesson applyLesson);
}

