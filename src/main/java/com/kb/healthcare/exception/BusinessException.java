package com.kb.healthcare.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;

@Getter
public class BusinessException extends NestedRuntimeException {

    private final int code;

    public BusinessException(
            int code,
            String msg
    ) {
        super(msg);

        this.code = code;
    }

    public BusinessException(
            int code,
            String msg,
            Throwable cause
    ) {
        super(
                msg,
                cause
        );

        this.code = code;
    }

}

