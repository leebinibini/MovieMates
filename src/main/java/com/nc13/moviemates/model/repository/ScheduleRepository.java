package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
