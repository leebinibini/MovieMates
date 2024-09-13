package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.ReservationEntity;
import com.nc13.moviemates.model.querydsl.ReservationQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>, ReservationQueryDSL {
}
