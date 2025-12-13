package com.kb.healthcare.exception;

record RestErrorResponse(
        int code,
        String message
) {
}
