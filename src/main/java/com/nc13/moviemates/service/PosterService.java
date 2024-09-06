package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.PosterEntity;

import java.util.List;
import java.util.Optional;

public interface PosterService {
    List<PosterEntity> findAll();

    PosterEntity save(PosterEntity email);

    Optional<PosterEntity> findById(Long id);

    boolean existsById(Long id);

    Long count();

    Integer deleteById(Long id);
}
