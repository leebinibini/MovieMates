package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.ScheduleEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<ScheduleEntity> findAll();

    ScheduleEntity save(ScheduleEntity email);

    Optional<ScheduleEntity> findById(Long id);

    boolean existsById(Long id);

    Long count();

    Integer deleteById(Long id);
}
