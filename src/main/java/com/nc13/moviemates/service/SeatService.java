package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;

import java.util.Date;
import java.util.List;

public interface SeatService {
    List <SeatEntity> getAll();
    SeatEntity getById();
    Boolean exists(Long id);
    Long getRowCount (Long id);
    List<SeatEntity> findSeatsByScheduleId(Long scheduleId);
}
