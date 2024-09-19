package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.queryDsl.MovieQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>, MovieQueryDSL {
}
