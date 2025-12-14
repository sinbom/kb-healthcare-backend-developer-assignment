package com.kb.healthcare.user.application.port.out;

public interface VerifyJwtPort {

    boolean verify(String token);

    String extractSubject(String token);

}
