package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;

import java.util.List;

public interface ReviewQueryDSL {
    List<ReviewEntity> getAll();
    ReviewEntity getById(Long id);

    List<MovieEntity> findWatchedMoviesByUserId(Long userId);

    Boolean exists(Long id);
    Long getRowCount();
    Long deleteMany(List<Long> reviewIdList);
    List<ReviewEntity> findAllByMovieId(Long movieId);
}
