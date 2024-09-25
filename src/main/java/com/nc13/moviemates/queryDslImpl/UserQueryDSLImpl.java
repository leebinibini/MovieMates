package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QUserEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.queryDsl.UserQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class UserQueryDSLImpl implements UserQueryDSL {
    @PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QUserEntity qUser = QUserEntity.userEntity;

    @Override
    public UserEntity findByEmail(String email) {
        return jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.email.eq(email))
                .fetchOne();
    }

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
