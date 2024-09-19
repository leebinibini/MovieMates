package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.queryDsl.ReservationQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>, ReservationQueryDSL {
}
