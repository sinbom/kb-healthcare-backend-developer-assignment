package com.kb.healthcare.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class DefaultAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(
            Throwable exception,
            Method method,
            Object... params
    ) {
        log.error(
                "Async exception",
                exception
        );

        // monitoring alert logic
    }

}
