package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.QTheaterEntity;
import com.nc13.moviemates.model.entity.TheaterEntity;
import com.nc13.moviemates.model.querydsl.TheaterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class TheaterQueryDSLImpl implements TheaterQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QTheaterEntity qTheater = QTheaterEntity.theaterEntity;
    @Override
    public List<TheaterEntity> getAll() {
        return jpaQueryFactory.selectFrom(qTheater).fetch();
    }

    @Override
    public TheaterEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qTheater.id.count()).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qTheater).where(qTheater.id.eq(id)).fetchCount()>0;
    }
}
