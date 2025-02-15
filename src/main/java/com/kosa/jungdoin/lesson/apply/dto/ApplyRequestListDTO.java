package com.kosa.jungdoin.lesson.apply.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyRequestListDTO {
    private Long applyId;           // 신청 ID
    private Long memberId;          // 신청한 회원 ID
    private String memberName;      // 신청한 회원 이름
    private String memberContent;   // 신청 시 작성한 문의 내용
    private String memberPhone;     // 신청한 회원 연락처
}