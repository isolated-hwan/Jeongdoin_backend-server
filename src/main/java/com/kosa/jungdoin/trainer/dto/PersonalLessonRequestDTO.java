package com.kosa.jungdoin.trainer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalLessonRequestDTO {
    private Long trainerId;
}
