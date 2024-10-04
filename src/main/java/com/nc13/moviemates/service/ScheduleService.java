package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.component.model.TheaterModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    Boolean saveSchedule(ScheduleModel schedule, String inputMovie, String inputTheater);

}
