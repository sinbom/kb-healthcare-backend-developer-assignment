package com.kb.healthcare.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.querydsl.jpa.DataNucleusTemplates.DEFAULT;

@Configuration
@EnableJpaAuditing
class JpaConfiguration {

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(
                DEFAULT,
                entityManager
        );
    }

}
