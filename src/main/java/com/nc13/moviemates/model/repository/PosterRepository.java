package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.model.querydsl.PosterQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<PosterEntity, Long>, PosterQueryDSL {
}