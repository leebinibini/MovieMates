package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SeatQueryDSL {
    List<SeatEntity> getAll();
    Optional <SeatEntity> findById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
    List<SeatEntity> findSeatsByScheduleId(Long scheduleId);



}
