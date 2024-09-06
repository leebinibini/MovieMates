package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
