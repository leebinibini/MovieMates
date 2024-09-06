package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, Long> {
}
