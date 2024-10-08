package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.queryDsl.TheaterQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<TheaterEntity, Long>, TheaterQueryDSL {
    @Query("SELECT t FROM TheaterEntity t WHERE t.name = :name")
    Optional<TheaterEntity> findByName(String name);
}
