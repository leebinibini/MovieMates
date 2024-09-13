package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QTheaterEntity;
import com.nc13.moviemates.model.entity.TheaterEntity;
import com.nc13.moviemates.model.querydsl.TheaterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TheaterQueryDSLImpl implements TheaterQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QTheaterEntity qTheater = QTheaterEntity.theaterEntity;

    @Override
    public List<TheaterEntity> getAll() {
        return List.of();
    }

    @Override
    public TheaterEntity getById(Long id) {
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
