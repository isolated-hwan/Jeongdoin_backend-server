package com.kosa.jungdoin.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	REGULAR("일반회원"), TRAINER("강사회원"), ADMIN("관리자");

	private final String role;
}
