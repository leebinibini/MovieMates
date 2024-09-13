package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.QWishEntity;
import com.nc13.moviemates.model.entity.WishEntity;
import com.nc13.moviemates.model.querydsl.WishQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class WishQueryDSLImpl implements WishQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QWishEntity qWish = QWishEntity.wishEntity;

    @Override
    public List<WishEntity> getAll() {
        return jpaQueryFactory.selectFrom(qWish).fetch();
    }

    @Override
    public WishEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qWish.id.count()).from(qWish).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qWish).where(qWish.id.eq(id)).fetchCount()>0;
    }
}
