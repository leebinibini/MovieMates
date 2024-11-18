package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface HistoryService {

    List<HistoryEntity> findAll();

    List<MovieEntity> findMovieByUserId(Long userId);

    Optional<HistoryEntity> findById(Long id);

    Boolean save(HistoryEntity email);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

    Optional<MovieEntity> findMovieForReview(Long userId, Long movieId);

    List<HistoryEntity> findByUserId(Long id);

    boolean hasWatchedMovie(Long userId, Long movieId);
}
