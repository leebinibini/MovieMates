package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieEntity> findAll();

    MovieEntity save(MovieEntity email);

    Optional<MovieEntity> findById(Long id);

    boolean existsById(Long id);

    Long count();

    Integer deleteById(Long id);


}
