package com.kosa.jungdoin.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	PENDING("처리 대기중 "), APPROVED("승인됨"), REJECTED("거절됨");

	private final String status;
}
