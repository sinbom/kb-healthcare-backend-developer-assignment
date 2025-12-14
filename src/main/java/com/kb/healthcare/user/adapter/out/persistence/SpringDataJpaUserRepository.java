package com.kb.healthcare.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByNickname(String nickname);

    Optional<UserEntity> findByEmail(String email);

}
