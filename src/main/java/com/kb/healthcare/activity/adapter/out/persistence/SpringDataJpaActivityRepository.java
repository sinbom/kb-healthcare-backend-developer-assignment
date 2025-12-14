package com.kb.healthcare.activity.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataJpaActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
