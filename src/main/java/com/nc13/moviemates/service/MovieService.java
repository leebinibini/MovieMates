package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieEntity> findAll();

    Boolean save(MovieEntity email);

    Optional<MovieEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);


}
