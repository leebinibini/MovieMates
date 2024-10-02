package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ScheduleQueryDSL {
    List<ScheduleEntity> getAll();
    ScheduleEntity getById(Long id);
    Boolean exists(Long id);
    List<OrderModel> findOrderByMovieId(Long movieId);
    Long getRowCount();
    List<ScheduleEntity> findByMovieId(Long movieId);
}
