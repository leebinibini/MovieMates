package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.ReservationEntity;

import java.util.List;

public interface ReservationQueryDSL {
    List<ReservationEntity> getAll();
    ReservationEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();

    Long deleteMany(List<Long> reservationIdList);
}
