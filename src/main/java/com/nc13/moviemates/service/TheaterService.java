package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.TheaterEntity;

import java.util.List;
import java.util.Optional;

public interface TheaterService {
    List<TheaterEntity> findAll();

    Boolean save(TheaterEntity email);

    Optional<TheaterEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Long deleteMany(List<Long> theaterIdList);

    Boolean deleteById(Long id);
}
