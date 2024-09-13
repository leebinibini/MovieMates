package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleQueryDSL {
    List<ScheduleEntity> getAll();
    ScheduleEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
