package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QReservationEntity;
import com.nc13.moviemates.model.entity.ReservationEntity;
import com.nc13.moviemates.model.querydsl.ReservationQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReservationQueryDSLImpl implements ReservationQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QReservationEntity qReservation = QReservationEntity.reservationEntity;

    @Override
    public List<ReservationEntity> getAll() {
        return List.of();
    }

    @Override
    public ReservationEntity getById(Long id) {
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
