package com.kb.healthcare.user.exception;

import com.kb.healthcare.exception.BusinessException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserAlreadyExistsException extends BusinessException {

    private static final int DEFAULT_STATUS = BAD_REQUEST.value();

    private static final String DEFAULT_MESSAGE = "이미 존재하는 유저에요.";

    public UserAlreadyExistsException() {
        this(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(String message) {
        super(
                DEFAULT_STATUS,
                message
        );
    }

    public UserAlreadyExistsException(
            String message,
            Throwable cause
    ) {
        super(
                DEFAULT_STATUS,
                message,
                cause
        );
    }

}
