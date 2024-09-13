package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QWishEntity;
import com.nc13.moviemates.model.entity.WishEntity;
import com.nc13.moviemates.model.querydsl.WishQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WishQueryDSLImpl implements WishQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QWishEntity qWish = QWishEntity.wishEntity;

    @Override
    public List<WishEntity> getAll() {
        return List.of();
    }

    @Override
    public WishEntity getById(Long id) {
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
