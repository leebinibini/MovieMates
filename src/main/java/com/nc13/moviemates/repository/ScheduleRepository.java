package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.queryDsl.ScheduleQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>, ScheduleQueryDSL {
}
