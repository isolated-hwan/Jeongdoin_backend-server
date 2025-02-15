package com.kosa.jungdoin.lesson.apply.repository;

import com.kosa.jungdoin.common.Status;
import com.kosa.jungdoin.entity.ApplyLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyLessonRepository extends JpaRepository<ApplyLesson, Long> {
    List<ApplyLesson> findByMemberMemberId(Long memberId);

    // 레슨 ID와 카테고리 코드로 PENDING 상태인 신청 목록 조회
    List<ApplyLesson> findByLessonIdAndLessonCategoryCodeLessonCategoryCodeAndStatus(
            Long lessonId,
            String lessonCategoryCode,
            Status status
    );

    @Modifying
    @Query("UPDATE ApplyLesson a SET a.status = 'APPROVED' WHERE a.id = :applyLessonId")
    int approveApplication(@Param("applyLessonId") Long applyLessonId);

    @Modifying
    @Query("UPDATE ApplyLesson a SET a.status = :status, a.trainerContent = :trainerContent WHERE a.applyLessonId = :applyId")
    void rejectApplication(@Param("applyId") Long applyId, @Param("status") Status status, @Param("trainerContent") String trainerContent);
}
