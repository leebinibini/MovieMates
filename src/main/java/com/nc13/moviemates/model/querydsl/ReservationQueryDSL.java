package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.ReservationEntity;

import java.util.List;

public interface ReservationQueryDSL {
    List<ReservationEntity> getAll();

    ReservationEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
