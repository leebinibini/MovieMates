package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.querydsl.core.Tuple;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<ScheduleEntity> findAll();

    Boolean save(ScheduleEntity email);


    List<OrderModel> findOrderByMovieId(Long movieId);

    List<ScheduleEntity> findByMovieId(Long movieId);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    ScheduleEntity findSchedule(Long theaterId, Long movieId, LocalDate showDate, LocalTime showTime);
}
