package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QReviewEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.queryDsl.ReviewQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewQueryDSLImpl implements ReviewQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReviewEntity qReview = QReviewEntity.reviewEntity;
    @Override
    public List<ReviewEntity> getAll() {
        return jpaQueryFactory.selectFrom(qReview).fetch();
    }

    @Override
    public ReviewEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qReview.id.count()).from(qReview).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qReview).where(qReview.id.eq(id)).fetchCount()>0;
    }
}