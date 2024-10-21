package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.WishEntity;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WishQueryDSL {
    List<WishEntity> getAll();
    WishEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();

    Optional<WishEntity> findByUserIdAndMovieId(Long userId, Long movieId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    List<Map<String, Object>> findWishesWithMovieDetails(Long userId);
}
