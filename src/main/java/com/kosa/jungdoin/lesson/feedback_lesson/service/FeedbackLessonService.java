package com.kosa.jungdoin.lesson.feedback_lesson.service;

import com.kosa.jungdoin.entity.FeedbackLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.feedback_lesson.dto.FeedbackLessonDTO;
import com.kosa.jungdoin.lesson.feedback_lesson.repository.FeedbackLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Transactional
public class FeedbackLessonService
        extends BaseLessonService<FeedbackLesson, FeedbackLessonDTO, FeedbackLesson.FeedbackLessonBuilder<?, ?>> {

    public FeedbackLessonService(FeedbackLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected FeedbackLessonDTO convertToDTO(FeedbackLesson lesson) {
        return FeedbackLessonDTO.builder()
                .lessonId(lesson.getFeedbackLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .content(lesson.getContent())
                .price(lesson.getPrice())
                .build();
    }

    @Override
    protected FeedbackLesson.FeedbackLessonBuilder<?, ?> getBuilder() {
        return FeedbackLesson.builder();
    }

    @Override
    protected Function<FeedbackLesson.FeedbackLessonBuilder<?, ?>, FeedbackLesson> getBuildFunction() {
        return FeedbackLesson.FeedbackLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(FeedbackLesson.FeedbackLessonBuilder<?, ?> builder, FeedbackLesson entity) {
        builder.feedbackLessonId(entity.getFeedbackLessonId())
                .trainer(entity.getTrainer())
                .content(entity.getContent())
                .price(entity.getPrice());
    }

    @Override
    protected void updateBuilderFromDTO(FeedbackLesson.FeedbackLessonBuilder<?, ?> builder, FeedbackLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        if (trainer != null)
            builder.trainer(trainer);
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
    }

    @Override
    protected FeedbackLesson getExistEntity(FeedbackLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    protected List<FeedbackLesson> findLessonsByTrainerId(Long trainerId) {
        FeedbackLessonRepository feedbackLessonRepository = (FeedbackLessonRepository) repository;
        return feedbackLessonRepository.findFeedbackLessonsByTrainerMemberId(trainerId);
    }

    @Override
    protected FeedbackLesson createEntityFromDTO(FeedbackLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return FeedbackLesson.builder()
                .trainer(trainer)
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }

    @Override
    protected FeedbackLesson updateEntityFromDTO(FeedbackLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return FeedbackLesson.builder()
                .feedbackLessonId(dto.getLessonId())
                .trainer(trainer)
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }
}
