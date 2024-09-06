package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.ScheduleEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<ScheduleEntity> findAll();

    Boolean save(ScheduleEntity email);

    Optional<ScheduleEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);
}
