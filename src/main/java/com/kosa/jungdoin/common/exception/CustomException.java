package com.kosa.jungdoin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	private final CustomExceptionCode exceptionCode;
}