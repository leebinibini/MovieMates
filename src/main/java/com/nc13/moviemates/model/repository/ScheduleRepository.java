package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.model.querydsl.ScheduleQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>, ScheduleQueryDSL {
}
