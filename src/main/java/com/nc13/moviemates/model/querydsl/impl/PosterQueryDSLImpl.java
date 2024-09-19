package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.model.entity.QPosterEntity;
import com.nc13.moviemates.model.querydsl.PosterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PosterQueryDSLImpl implements PosterQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPosterEntity qPoster = QPosterEntity.posterEntity;
    @Override
    public List<PosterEntity> getAll() {
        return jpaQueryFactory.selectFrom(qPoster).fetch();
    }

    @Override
    public PosterEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qPoster).where(qPoster.id.eq(id)).fetchCount()>0;
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qPoster.id.count()).from(qPoster).fetchOne();
    }
}
