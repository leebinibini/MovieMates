package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QUserEntity;
import com.nc13.moviemates.model.entity.UserEntity;
import com.nc13.moviemates.model.querydsl.UserQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserQueryDSLImpl implements UserQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QUserEntity qUser = QUserEntity.userEntity;

    @Override
    public List<UserEntity> getAll() {
        return List.of();
    }

    @Override
    public UserEntity getById(Long id) {
        return null;
    }

    @Override
    public Boolean exists(Long id) {
        return null;
    }

    @Override
    public Long getRowCount() {
        return 0L;
    }
}
