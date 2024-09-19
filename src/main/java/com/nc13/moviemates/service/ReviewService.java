package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.ReviewEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {

    List<?> findAll();

    Optional<ReviewEntity> findById(Long id);

    Boolean save(ReviewEntity review);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);
}
