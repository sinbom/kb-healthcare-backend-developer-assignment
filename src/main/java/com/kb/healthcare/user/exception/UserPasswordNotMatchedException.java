package com.kb.healthcare.user.exception;

import com.kb.healthcare.exception.BusinessException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserPasswordNotMatchedException extends BusinessException {

    private static final int DEFAULT_STATUS = BAD_REQUEST.value();

    private static final String DEFAULT_MESSAGE = "유저 정보가 일치하지 않아요.";

    public UserPasswordNotMatchedException() {
        this(DEFAULT_MESSAGE);
    }

    public UserPasswordNotMatchedException(String message) {
        super(
                DEFAULT_STATUS,
                message
        );
    }

    public UserPasswordNotMatchedException(
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
