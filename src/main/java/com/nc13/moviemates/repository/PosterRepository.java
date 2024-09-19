package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.queryDsl.PosterQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<PosterEntity, Long>, PosterQueryDSL {
}
