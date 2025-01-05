package com.kosa.jungdoin.lesson.common;

import com.kosa.jungdoin.common.annotation.RequireAuthorization;
import com.kosa.jungdoin.common.annotation.ValidateEntity;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Transactional
@RequiredArgsConstructor
public abstract class BaseLessonService<T, D, B> {

    protected final JpaRepository<T, Long> repository;
    protected final TrainerRepository trainerRepository;

    @Transactional(readOnly = true)
    public D getLesson(Long lessonId) {
        T lesson = repository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        return convertToDTO(lesson);
    }

    @Transactional(readOnly = true)
    public List<D> getAllLessons() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @ValidateEntity(entityClass = Trainer.class, idField = "trainerId")
    public List<D> getLessonsByTrainerId(Long trainerId) {
        List<T> lessons = findLessonsByTrainerId(trainerId);
        return lessons.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public D createLesson(D dto) {
        T entity = createEntityFromDTO(dto);
        T savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    public D updateLesson(D dto) {
        T updatedEntity = updateEntityFromDTO(dto);
        T savedEntity = repository.save(updatedEntity);
        return convertToDTO(savedEntity);
    }

    public D patchLesson(D dto) {
        T existingEntity = getExistEntity(dto);
        T patchedEntity = patchEntityFromDTO(existingEntity, dto);
        T savedEntity = repository.save(patchedEntity);
        return convertToDTO(savedEntity);
    }

    @RequireAuthorization
    public void deleteLesson(Long lessonId) {
        if (!repository.existsById(lessonId)) {
            throw new IllegalArgumentException("Lesson not found");
        }
        repository.deleteById(lessonId);
    }

    protected Trainer getTrainer(Long trainerId) {
        Optional<Trainer> opTrainer = trainerRepository.findById(trainerId);
        if (opTrainer.isEmpty()) {
            throw new IllegalArgumentException("Trainer not found");
        }
        return opTrainer.get();
    }

    protected T createEntityFromBuilder(B builder) {
        return getBuildFunction().apply(builder);
    }

    protected B initializeBuilder(T entity) {
        B builder = getBuilder();
        setCommonFields(builder, entity);
        return builder;
    }

    protected T patchEntityFromDTO(T entity, D dto) {
        B builder = initializeBuilder(entity);
        updateBuilderFromDTO(builder, dto);
        return createEntityFromBuilder(builder);
    }

    protected abstract D convertToDTO(T lesson);

    protected abstract T createEntityFromDTO(D dto);

    protected abstract T updateEntityFromDTO(D dto);

    protected abstract B getBuilder();

    protected abstract Function<B, T> getBuildFunction();

    protected abstract void setCommonFields(B builder, T entity);

    protected abstract void updateBuilderFromDTO(B builder, D dto);

    protected abstract T getExistEntity(D dto);

    protected abstract List<T> findLessonsByTrainerId(Long trainerId);
}
