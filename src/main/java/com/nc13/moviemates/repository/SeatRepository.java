package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.QSeatEntity;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.queryDsl.SeatQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<SeatEntity, Long>, SeatQueryDSL {
}
