package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.PaymentEntity;
import com.nc13.moviemates.model.entity.ScheduleEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleQueryDSL {
    List<ScheduleEntity> getAll();

    ScheduleEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
