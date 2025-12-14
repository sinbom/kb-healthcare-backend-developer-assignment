package com.kb.healthcare.user.application.port.out;

import com.kb.healthcare.user.domain.User;

import java.util.Optional;

public interface FindUserPort {

    Optional<User> findById(String id);

    Optional<User> findByNickname(String email);

    Optional<User> findByEmail(String email);

}
