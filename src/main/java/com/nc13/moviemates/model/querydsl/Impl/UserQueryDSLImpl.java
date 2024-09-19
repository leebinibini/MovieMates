package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.QUserEntity;
import com.nc13.moviemates.model.entity.UserEntity;
import com.nc13.moviemates.model.querydsl.UserQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class UserQueryDSLImpl implements UserQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QUserEntity qUser = QUserEntity.userEntity;
    @Override
    public List<UserEntity> getAll() {
        return jpaQueryFactory.selectFrom(qUser).fetch();
    }

    @Override
    public UserEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qUser.id.count()).from(qUser).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchCount()>0;
    }
}
