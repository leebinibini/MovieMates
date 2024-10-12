package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<ReviewEntity> findAll();

    Optional<ReviewEntity> findById(Long id);

    Boolean save(ReviewEntity review);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);


    Long deleteMany(List<Long> reviewIdList);

    List<MovieEntity> getWatchedMoviesByUserId(Long userId);
}
