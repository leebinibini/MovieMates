package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.PosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosterRepository extends JpaRepository<PosterEntity, Long> {
}
