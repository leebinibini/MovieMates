package com.nc13.moviemates.config;

import com.querydsl.jpa.impl.JPAQueryFactory;  // 이 부분을 추가
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);  // 올바른 클래스 사용
    }
}