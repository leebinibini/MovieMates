package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.TheaterEntity;
import com.nc13.moviemates.model.querydsl.TheaterQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<TheaterEntity, Long>, TheaterQueryDSL {
}
