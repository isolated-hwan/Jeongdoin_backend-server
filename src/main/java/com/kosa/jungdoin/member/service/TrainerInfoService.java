package com.kosa.jungdoin.member.service;

import com.kosa.jungdoin.entity.BaseMember;
import com.kosa.jungdoin.entity.ExerciseCategory;
import com.kosa.jungdoin.entity.TimeTable;
import com.kosa.jungdoin.entity.Trainer;
import com.kosa.jungdoin.entity.TrainerProfile;
import com.kosa.jungdoin.entity.TrainerProfileCategory;
import com.kosa.jungdoin.member.dto.TrainerInfoDTO;
import com.kosa.jungdoin.member.repository.BaseMemberRepository;
import com.kosa.jungdoin.member.repository.ExerciseCategoryRepository;
import com.kosa.jungdoin.member.repository.TimeTableRepository;
import com.kosa.jungdoin.member.repository.TrainerProfileCategoryRepository;
import com.kosa.jungdoin.member.repository.TrainerProfileRepository;
import com.kosa.jungdoin.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerInfoService {

    private final TrainerRepository trainerRepository;
    private final TrainerProfileRepository trainerProfileRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final TimeTableRepository timeTableRepository;
    private final TrainerProfileCategoryRepository trainerProfileCategoryRepository;
    private final BaseMemberRepository baseMemberRepository;

    @Transactional
    public Map<String, Object> saveTrainerProfile(List<TrainerInfoDTO> profiles) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Long> savedIdList = new ArrayList<>();
        BaseMember member = baseMemberRepository.findById(profiles.getLast().getMemberId())
                .orElseThrow(() -> new RuntimeException("member not found"));
        for (TrainerInfoDTO profile : profiles) {
            TrainerProfileCategory category = trainerProfileCategoryRepository.findById(profile.getCategoryCode())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            TrainerProfile trainerProfile = TrainerProfile.builder()
                    .member(member)
                    .categoryCode(category)
                    .title(profile.getTitle())
                    .startDate(profile.getStartDate())
                    .endDate(profile.getEndDate())
                    .detail(profile.getDetail())
                    .build();

            TrainerProfile savedProfile = trainerProfileRepository.save(trainerProfile);
            savedIdList.add(savedProfile.getTrainerProfileId());
        }
        resultMap.put("memberId", member.getMemberId());
        resultMap.put("profileIdList", savedIdList);
        return resultMap;
    }

    @Transactional(readOnly = true)
    public List<TrainerInfoDTO> getTrainerProfiles(Long memberId) {
        List<TrainerProfile> profiles = trainerProfileRepository.findTrainerProfilesByMember_MemberId(memberId);
        return profiles.stream()
                .map(this::convertToDto)
                .toList();
    }

    private TrainerInfoDTO convertToDto(TrainerProfile profile) {
        return TrainerInfoDTO.builder()
                .trainerProfileId(profile.getTrainerProfileId())
                .memberId(profile.getMember().getMemberId())
                .categoryCode(profile.getCategoryCode().getCategoryCode())
                .categoryName(profile.getCategoryCode().getCategoryName())
                .title(profile.getTitle())
                .startDate(profile.getStartDate())
                .endDate(profile.getEndDate())
                .detail(profile.getDetail())
                .build();
    }

    @Transactional(readOnly = true)
    public List<TrainerInfoDTO> getAllExerciseCategories() {
        return exerciseCategoryRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public void updateTrainerCategory(Long trainerId, String exerciseCategoryCode) {
        BaseMember existingMember = baseMemberRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        ExerciseCategory category = exerciseCategoryRepository.findById(exerciseCategoryCode)
                .orElseThrow(() -> new RuntimeException("Exercise category not found"));

        Trainer updatedTrainer = Trainer.builder()
                .memberId(existingMember.getMemberId())
                .baseMember(existingMember)
                .exerciseCategory(category)
                .build();

        trainerRepository.save(updatedTrainer);
    }

    private TrainerInfoDTO convertToDto(ExerciseCategory category) {
        return TrainerInfoDTO.builder()
                .exerciseCategoryCode(category.getExerciseCategoryCode())
                .exerciseCategoryName(category.getCategoryName())
                .build();
    }

    @Transactional(readOnly = true)
    public TrainerInfoDTO getTrainerInfo(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        return TrainerInfoDTO.builder()
                .memberId(trainer.getMemberId())
                .exerciseCategoryCode(trainer.getExerciseCategory().getExerciseCategoryCode())
                .exerciseCategoryName(trainer.getExerciseCategory().getCategoryName())
                .build();
    }

    @Transactional
    public void updateTimeTables(Long trainerId, List<Map<String, Object>> timeTableUpdates) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        // 기존의 모든 타임테이블 항목을 가져옴
        List<TimeTable> existingTimeTables = timeTableRepository.findByTrainer(trainer);

        // 업데이트할 타임테이블 항목을 저장할 Set을 생성
        Set<String> updatedTimeSlots = new HashSet<>();

        for (Map<String, Object> update : timeTableUpdates) {
            String day = (String) update.get("day");
            int startHour = Integer.parseInt(String.valueOf(update.get("startHour")));
            boolean isSelected = (boolean) update.get("isSelected");

            String timeSlotKey = day + "-" + startHour;

            if (isSelected) {
                updatedTimeSlots.add(timeSlotKey);

                // 이미 존재하지 않는 경우에만 새로운 항목을 추가
                if (existingTimeTables.stream().noneMatch(tt -> tt.getDay().equals(day) && tt.getStartTime().getHour() == startHour)) {
                    LocalDate currentDate = LocalDate.now();
                    LocalDateTime startTime = LocalDateTime.of(currentDate, LocalTime.of(startHour, 0));
                    LocalDateTime endTime = startTime.plusHours(1);

                    TimeTable newTimeTable = TimeTable.builder()
                            .trainer(trainer)
                            .day(day)
                            .startTime(startTime)
                            .endTime(endTime)
                            .build();
                    timeTableRepository.save(newTimeTable);
                }
            }
        }

        // 업데이트되지 않은 기존 항목을 삭제합니다.
        existingTimeTables.forEach(timeTable -> {
            String timeSlotKey = timeTable.getDay() + "-" + timeTable.getStartTime().getHour();
            if (!updatedTimeSlots.contains(timeSlotKey)) {
                timeTableRepository.delete(timeTable);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTimeTableByTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        List<TimeTable> timeTables = timeTableRepository.findByTrainer(trainer);

        return timeTables.stream()
                .map(timeTable -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("day", timeTable.getDay());
                    map.put("startTime", timeTable.getStartTime());
                    map.put("endTime", timeTable.getEndTime());
                    return map;
                })
                .collect(Collectors.toList());
    }
}