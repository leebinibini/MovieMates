package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface HistoryQueryDSL {

    Optional<MovieEntity> findMovieForReview(Long userId, Long movieId);
    List<MovieEntity> findMovieByUserId(Long userId);
    List<HistoryEntity> getAll();

    Optional<HistoryEntity> findById(Long id);

    Boolean exists(Long id);

    Long getRowCount();
    Optional<HistoryEntity> findByHistoryId(Long id);

    List<HistoryEntity> findByUserId(Long id);
}
