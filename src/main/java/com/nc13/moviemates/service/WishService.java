package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.WishModel;
import com.nc13.moviemates.entity.WishEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface WishService {
    List<?> findAll();

    Optional<WishEntity> findById(Long id);

    Optional<WishEntity> findByMovieIdandUserId(Long movieId, Long userId);

    Boolean save(WishModel wish);

    Boolean deleteById(Long id);

    Boolean delete(WishModel wish);

    Long count();

    Boolean existsById(Long id);

    Boolean existsByMovieIdandUserId(Long movieId, Long userId);

    Boolean isWishlisted(Long movieId, Long userId);

    List<Map<String, Object>> findWishesWithMovieDetails(Long userId);
}
