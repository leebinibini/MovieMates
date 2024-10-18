package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.QUserEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.queryDsl.UserQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
    public Boolean existsByPassword(String password) {
        return jpaQueryFactory.selectFrom(qUser)
                .where(qUser.password.eq(password))
                .fetchFirst() != null;
    }

    @Override
    public void insert(UserEntity user){

        QUserEntity qUser = QUserEntity.userEntity;
        jpaQueryFactory
                .insert(qUser)
                .columns(qUser.email, qUser.password, qUser.nickname, qUser.tel, qUser.gender)
                .values(user.getEmail(), user.getPassword(), user.getNickname(), user.getTel(), user.getGender())
                .execute();
    }


    @Override
    @Transactional
    public void update(UserModel user) {
        QUserEntity qUser = QUserEntity.userEntity;

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qUser);
        updateClause
                .where(qUser.id.eq(user.getId()))
                .set(qUser.email, user.getEmail())
                .set(qUser.password, user.getPassword())
                .set(qUser.nickname, user.getNickname())
                .set(qUser.gender, user.getGender())
                .set(qUser.tel, user.getTel())
                .execute();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchCount()>0;
    }

    @Override
    public Boolean exitsByEmail(String email){
        return jpaQueryFactory.selectFrom(qUser).where(qUser.email.eq(email)).fetchCount()>0;}

    @Override
    public void updateUserInfo(UserModel userData, String profileImageUrl) {
        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qUser);

        updateClause
                .where(qUser.id.eq(userData.getId()))
                .set(qUser.email, userData.getEmail())
                .set(qUser.password, userData.getPassword())
                .set(qUser.nickname, userData.getNickname())
                .set(qUser.gender, userData.getGender())
                .set(qUser.tel, userData.getTel())
                .set(qUser.profileImageUrl, profileImageUrl)  // 이미지 URL 업데이트
                .execute();
    }


}
