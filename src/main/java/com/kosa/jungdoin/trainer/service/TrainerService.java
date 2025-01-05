package com.kosa.jungdoin.trainer.service;

import com.kosa.jungdoin.trainer.dto.AllLessonsDTO;
import com.kosa.jungdoin.lesson.feedback_lesson.service.FeedbackLessonService;
import com.kosa.jungdoin.lesson.group_lesson.service.GroupLessonService;
import com.kosa.jungdoin.lesson.online_lesson.service.OnlineLessonService;
import com.kosa.jungdoin.lesson.personal_lesson.service.PersonalLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final PersonalLessonService personalLessonService;
    private final GroupLessonService groupLessonService;
    private final OnlineLessonService onlineLessonService;
    private final FeedbackLessonService feedbackLessonService;

    public AllLessonsDTO getAllLessonsByTrainerId(Long trainerId) {
        return AllLessonsDTO.builder()
                .personalLessons(personalLessonService.getLessonsByTrainerId(trainerId))
                .groupLessons(groupLessonService.getLessonsByTrainerId(trainerId))
                .onlineLessons(onlineLessonService.getLessonsByTrainerId(trainerId))
                .feedbackLessons(feedbackLessonService.getLessonsByTrainerId(trainerId))
                .build();
    }
}
