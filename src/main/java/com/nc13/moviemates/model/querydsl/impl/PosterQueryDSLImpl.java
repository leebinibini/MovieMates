package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.model.entity.QPosterEntity;
import com.nc13.moviemates.model.querydsl.PosterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PosterQueryDSLImpl implements PosterQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QPosterEntity qPoster = QPosterEntity.posterEntity;


    @Override
    public List<PosterEntity> getAll() {
        return List.of();
    }

    @Override
    public PosterEntity getById(Long id) {
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
