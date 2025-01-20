package com.kosa.jungdoin.lesson.online_lesson.service;

import com.kosa.jungdoin.entity.OnlineLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.online_lesson.dto.OnlineLessonDTO;
import com.kosa.jungdoin.lesson.online_lesson.repository.OnlineLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class OnlineLessonService
        extends BaseLessonService<OnlineLesson, OnlineLessonDTO, OnlineLesson.OnlineLessonBuilder<?, ?>> {

    public OnlineLessonService(OnlineLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected OnlineLessonDTO convertToDTO(OnlineLesson lesson) {
        return OnlineLessonDTO.builder()
                .lessonId(lesson.getOnlineLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .trainerName(lesson.getTrainer().getBaseMember().getUsername())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .price(lesson.getPrice())
                .category(lesson.getTrainer().getExerciseCategory().getCategoryName())
                .build();
    }

    @Override
    protected OnlineLesson.OnlineLessonBuilder<?, ?> getBuilder() {
        return OnlineLesson.builder();
    }

    @Override
    protected Function<OnlineLesson.OnlineLessonBuilder<?, ?>, OnlineLesson> getBuildFunction() {
        return OnlineLesson.OnlineLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(OnlineLesson.OnlineLessonBuilder<?, ?> builder, OnlineLesson entity) {
        builder.onlineLessonId(entity.getOnlineLessonId())
                .trainer(entity.getTrainer())
                .title(entity.getTitle())
                .content(entity.getContent())
                .price(entity.getPrice());
    }

    @Override
    protected void updateBuilderFromDTO(OnlineLesson.OnlineLessonBuilder<?, ?> builder, OnlineLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        if (trainer != null)
            builder.trainer(trainer);
        if (dto.getTitle() != null)
            builder.title(dto.getTitle());
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
    }

    @Override
    protected OnlineLesson getExistEntity(OnlineLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    protected List<OnlineLesson> findLessonsByTrainerId(Long trainerId) {
        OnlineLessonRepository onlineLessonRepository = (OnlineLessonRepository) repository;
        return onlineLessonRepository.findOnlineLessonsByTrainerMemberId(trainerId);
    }

    public List<OnlineLessonDTO> findByTrainerId(Long trainerId) {
        List<OnlineLesson> lessons = findLessonsByTrainerId(trainerId);  // 기존 메서드 활용
        return lessons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected OnlineLesson createEntityFromDTO(OnlineLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return OnlineLesson.builder()
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }

    @Override
    protected OnlineLesson updateEntityFromDTO(OnlineLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return OnlineLesson.builder()
                .onlineLessonId(dto.getLessonId())
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .build();
    }
}
