package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QReviewEntity;
import com.nc13.moviemates.model.entity.ReviewEntity;
import com.nc13.moviemates.model.querydsl.ReviewQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewQueryDSLImpl implements ReviewQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QReviewEntity qReview = QReviewEntity.reviewEntity;

    @Override
    public List<ReviewEntity> getAll() {
        return List.of();
    }

    @Override
    public ReviewEntity getById(Long id) {
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
