package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.QReservationEntity;
import com.nc13.moviemates.model.entity.ReservationEntity;
import com.nc13.moviemates.model.querydsl.ReservationQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ReservationQueryDSLImpl implements ReservationQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReservationEntity qReservation = QReservationEntity.reservationEntity;
    @Override
    public List<ReservationEntity> getAll() {
        return jpaQueryFactory.selectFrom(qReservation).fetch();
    }

    @Override
    public ReservationEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qReservation.id.count()).from(qReservation).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qReservation).where(qReservation.id.eq(id)).fetchCount()>0;
    }
}
