package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.TheaterEntity;

import java.util.List;
import java.util.Optional;

public interface TheaterService {
    List<TheaterEntity> findAll();

    TheaterEntity save(TheaterEntity email);

    Optional<TheaterEntity> findById(Long id);

    boolean existsById(Long id);

    Long count();

    Integer deleteById(Long id);
}
