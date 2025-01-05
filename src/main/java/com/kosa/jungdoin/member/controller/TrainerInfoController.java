package com.kosa.jungdoin.member.controller;

import com.kosa.jungdoin.member.dto.TrainerInfoDTO;
import com.kosa.jungdoin.member.service.TrainerInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
@Slf4j
public class TrainerInfoController {

	private final TrainerInfoService trainerInfoService;

	@GetMapping("/profile")
	public ResponseEntity<List<TrainerInfoDTO>> getTrainerProfiles(@RequestParam("memberId") Long memberId) {
		List<TrainerInfoDTO> profiles = trainerInfoService.getTrainerProfiles(memberId);
		return ResponseEntity.ok(profiles);
	}

	@GetMapping("/exercise-categories")
	public ResponseEntity<List<TrainerInfoDTO>> getAllExerciseCategories() {
		return ResponseEntity.ok(trainerInfoService.getAllExerciseCategories());
	}

	@PutMapping("/{trainerId}/update-category")
	public ResponseEntity<Void> updateTrainerCategory(
		@PathVariable("trainerId") Long trainerId,
		@RequestParam("exerciseCategoryCode") String exerciseCategoryCode) {
		trainerInfoService.updateTrainerCategory(trainerId, exerciseCategoryCode);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{trainerId}")
	public ResponseEntity<TrainerInfoDTO> getTrainerInfo(@PathVariable("trainerId") Long trainerId) {
		TrainerInfoDTO trainerInfo = trainerInfoService.getTrainerInfo(trainerId);
		return ResponseEntity.ok(trainerInfo);
	}

	@PutMapping("/timetable/{trainerId}")
	public ResponseEntity<Void> updateTimeTable(@PathVariable("trainerId") Long trainerId, @RequestBody List<Map<String, Object>> timeTableUpdates) {
		trainerInfoService.updateTimeTables(trainerId, timeTableUpdates);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/timetable/{trainerId}")
	public ResponseEntity<List<Map<String, Object>>> getTimeTable(@PathVariable("trainerId") Long trainerId) {
		List<Map<String, Object>> timeTable = trainerInfoService.getTimeTableByTrainer(trainerId);
		return ResponseEntity.ok(timeTable);
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveTrainerProfiles(@RequestBody List<TrainerInfoDTO> profiles) {
		Map<String, Object> resultMap = trainerInfoService.saveTrainerProfile(profiles);
		List<Long> profileIdList = (List<Long>) resultMap.get("profileIdList");
		if (profileIdList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(resultMap);
	}
	
}
