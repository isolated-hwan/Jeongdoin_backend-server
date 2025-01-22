package com.kosa.jungdoin.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Process {
	NOT_STARTED("시작 전"), IN_PROGRESS("진행 중"), HOLD("일시 중단"), CANCELLED("취소"), COMPLETED("완료");

	private final String process;
}
