package com.kosa.jungdoin.lesson.personal_lesson.service;

import com.kosa.jungdoin.entity.PersonalLesson;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.lesson.common.BaseLessonService;
import com.kosa.jungdoin.lesson.personal_lesson.dto.PersonalLessonDTO;
import com.kosa.jungdoin.lesson.personal_lesson.repository.PersonalLessonRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Transactional
public class PersonalLessonService
        extends BaseLessonService<PersonalLesson, PersonalLessonDTO, PersonalLesson.PersonalLessonBuilder<?, ?>> {

    public PersonalLessonService(PersonalLessonRepository repository, TrainerRepository trainerRepository) {
        super(repository, trainerRepository);
    }

    @Override
    protected PersonalLessonDTO convertToDTO(PersonalLesson lesson) {
        return PersonalLessonDTO.builder()
                .lessonId(lesson.getPersonalLessonId())
                .trainerId(lesson.getTrainer().getMemberId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .price(lesson.getPrice())
                .lat(lesson.getLat())
                .lng(lesson.getLng())
                .build();
    }

    @Override
    protected PersonalLesson.PersonalLessonBuilder<?, ?> getBuilder() {
        return PersonalLesson.builder();
    }

    @Override
    protected Function<PersonalLesson.PersonalLessonBuilder<?, ?>, PersonalLesson> getBuildFunction() {
        return PersonalLesson.PersonalLessonBuilder::build;
    }

    @Override
    protected void setCommonFields(PersonalLesson.PersonalLessonBuilder<?, ?> builder, PersonalLesson entity) {
        builder.personalLessonId(entity.getPersonalLessonId())
                .trainer(entity.getTrainer())
                .title(entity.getTitle())
                .content(entity.getContent())
                .price(entity.getPrice())
                .lat(entity.getLat())
                .lng(entity.getLng());
    }

    @Override
    protected void updateBuilderFromDTO(PersonalLesson.PersonalLessonBuilder<?, ?> builder, PersonalLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        if (trainer != null)
            builder.trainer(trainer);
        if (dto.getTitle() != null)
            builder.title(dto.getTitle());
        if (dto.getContent() != null)
            builder.content(dto.getContent());
        if (dto.getPrice() != null)
            builder.price(dto.getPrice());
        if (dto.getLat() != null)
            builder.lat(dto.getLat());
        if (dto.getLng() != null)
            builder.lng(dto.getLng());
    }

    @Override
    protected PersonalLesson getExistEntity(PersonalLessonDTO dto) {
        return repository.findById(dto.getLessonId()).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found")
        );
    }

    @Override
    protected List<PersonalLesson> findLessonsByTrainerId(Long trainerId) {
        PersonalLessonRepository personalLessonRepository = (PersonalLessonRepository) repository;
        return personalLessonRepository.findPersonalLessonsByTrainerMemberId(trainerId);
    }

    @Override
    protected PersonalLesson createEntityFromDTO(PersonalLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return PersonalLesson.builder()
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }

    @Override
    protected PersonalLesson updateEntityFromDTO(PersonalLessonDTO dto) {
        Trainer trainer = getTrainer(dto.getTrainerId());
        return PersonalLesson.builder()
                .personalLessonId(dto.getLessonId())
                .trainer(trainer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }
}
