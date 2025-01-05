package com.kosa.jungdoin.trainer.dto;

import com.kosa.jungdoin.lesson.personal_lesson.dto.PersonalLessonDTO;
import com.kosa.jungdoin.lesson.group_lesson.dto.GroupLessonDTO;
import com.kosa.jungdoin.lesson.online_lesson.dto.OnlineLessonDTO;
import com.kosa.jungdoin.lesson.feedback_lesson.dto.FeedbackLessonDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllLessonsDTO {
    private List<PersonalLessonDTO> personalLessons;
    private List<GroupLessonDTO> groupLessons;
    private List<OnlineLessonDTO> onlineLessons;
    private List<FeedbackLessonDTO> feedbackLessons;
}
