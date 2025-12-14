package com.kb.healthcare.user.exception;

import com.kb.healthcare.exception.BusinessException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserNotExistsException extends BusinessException {

    private static final int DEFAULT_STATUS = BAD_REQUEST.value();

    private static final String DEFAULT_MESSAGE = "존재하지 않는 유저에요.";

    public UserNotExistsException() {
        this(DEFAULT_MESSAGE);
    }

    public UserNotExistsException(String message) {
        super(
                DEFAULT_STATUS,
                message
        );
    }

    public UserNotExistsException(
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
