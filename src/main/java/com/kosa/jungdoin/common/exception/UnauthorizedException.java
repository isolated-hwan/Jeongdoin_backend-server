package com.kosa.jungdoin.common.exception;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException() {
        super(CustomExceptionCode.UNAUTHORIZED_ACCESS);
    }

    public UnauthorizedException(String message) {
        super(CustomExceptionCode.INSUFFICIENT_PERMISSIONS);
    }
}