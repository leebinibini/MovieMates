package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.ReservationEntity;

import java.util.List;

public interface ReservationQueryDSL {
    List<ReservationEntity> getAll();
    ReservationEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
